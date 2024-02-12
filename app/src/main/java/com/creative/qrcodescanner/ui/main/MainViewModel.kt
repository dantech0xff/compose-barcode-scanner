package com.creative.qrcodescanner.ui.main

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.data.entity.QRCodeContact
import com.creative.qrcodescanner.data.entity.QRCodePhone
import com.creative.qrcodescanner.data.entity.QRCodeSMS
import com.creative.qrcodescanner.data.entity.QRCodeURL
import com.creative.qrcodescanner.data.entity.QRCodeWifi
import com.creative.qrcodescanner.data.entity.toQRCodeEntity
import com.creative.qrcodescanner.repo.user.UserDataRepo
import com.creative.qrcodescanner.ui.result.QRCodeRawData
import com.creative.qrcodescanner.usecase.history.InsertQRCodeHistoryFlowUseCase
import com.creative.qrcodescanner.usecase.setting.UpdateKeepScanningSettingUseCase
import com.google.mlkit.vision.barcode.common.Barcode
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@Stable
data class MainUIState(
    val isFrontCamera: Boolean = false,
    val isTorchOn: Boolean = false,
    val isLoading: Boolean = false,
    val isQRCodeFound: Boolean = false,
)

sealed class QRCodeAction {
    data object None : QRCodeAction()
    data class ToastAction(val message: String) : QRCodeAction()
    data class OpenUrl(val url: String) : QRCodeAction()
    data class CopyText(val text: String) : QRCodeAction()
    data class ContactInfo(val contact: QRCodeContact) : QRCodeAction()
    data class TextSearchGoogle(val text: String) : QRCodeAction()
    data class TextShareAction(val text: String) : QRCodeAction()
    data class SendSMSAction(val sms: QRCodeSMS) : QRCodeAction()
    data class CallPhoneAction(val phone: String) : QRCodeAction()
    data class OpenQRCodeResult(val id: Int) : QRCodeAction()
    data class PickGalleryImage(val uri: Uri) : QRCodeAction()
}

interface ICameraController {
    fun toggleTorch()
    fun toggleCamera()
    fun toggleKeepScanning()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertQRCodeHistoryUseCase: InsertQRCodeHistoryFlowUseCase,
    private val moshi: Moshi,
    userDataRepo: UserDataRepo,
    private val updateKeepScanningSettingUseCase: UpdateKeepScanningSettingUseCase,
    @ApplicationContext private val applicationContext: Context
) : ViewModel(), ICameraController {

    companion object {
        const val INVALID_DB_ROW_ID = 0
    }

    private val setQRResults: MutableSet<String> = mutableSetOf()

    val appSettingState = userDataRepo.userSettingData.stateIn(
        viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    private val _mainUiState: MutableStateFlow<MainUIState> = MutableStateFlow(MainUIState())
    val mainUiState: StateFlow<MainUIState> = _mainUiState.asStateFlow()

    private val _qrCodeActionState: MutableSharedFlow<QRCodeAction> = MutableSharedFlow(extraBufferCapacity = 1)
    val qrCodeActionState = _qrCodeActionState.asSharedFlow()

    override fun toggleTorch() {
        _mainUiState.value = _mainUiState.value.let {
            it.copy(isTorchOn = !it.isTorchOn)
        }
    }

    fun scanQRSuccess(result: Barcode) {
        if (mainUiState.value.isQRCodeFound || (setQRResults.contains(result.rawValue) && appSettingState.value?.isKeepScanning == true)) {
            return
        }

        setQRResults.add(result.rawValue ?: return)
        _mainUiState.value = _mainUiState.value.copy(isQRCodeFound = true)
        viewModelScope.launch {
            insertQRCodeHistoryUseCase.execute(
                input = result.toQRCodeEntity()
            ).collectLatest {
                if (appSettingState.value?.isKeepScanning != true) {
                    _qrCodeActionState.tryEmit(QRCodeAction.OpenQRCodeResult(it.toInt()))
                } else {
                    _qrCodeActionState.tryEmit(QRCodeAction.ToastAction("QR Code Scanned ${result.rawValue}"))
                    resetScanQR()
                }
            }
        }
    }

    fun resetScanQR() {
        _mainUiState.value = _mainUiState.value.copy(isQRCodeFound = false)
        _qrCodeActionState.tryEmit(QRCodeAction.None)
    }

    override fun toggleCamera() {
        _mainUiState.value = _mainUiState.value.let {
            it.copy(isFrontCamera = !it.isFrontCamera)
        }
    }

    override fun toggleKeepScanning() {
        viewModelScope.launch {
            if (appSettingState.value?.isPremium != true) {
                _qrCodeActionState.tryEmit(QRCodeAction.ToastAction(applicationContext.getString(R.string.this_feature_is_only_available_for_premium_user)))
            } else {
                updateKeepScanningSettingUseCase.execute(!(appSettingState.value?.isKeepScanning ?: false))
            }
        }
    }

    fun handleBarcodeResult(barcode: QRCodeRawData) {
        when (barcode.type) {
            Barcode.TYPE_URL -> {
                barcode.jsonDetails?.let {
                    runCatching {
                        moshi.adapter(QRCodeURL::class.java).fromJson(it)?.let {
                            _qrCodeActionState.tryEmit(QRCodeAction.OpenUrl(it.url.orEmpty()))
                        } ?: throw Exception("QRCodeURL is null")
                    }.onFailure {
                        _qrCodeActionState.tryEmit(QRCodeAction.CopyText(barcode.rawData))
                    }
                } ?: run {
                    _qrCodeActionState.tryEmit(QRCodeAction.CopyText(barcode.rawData))
                }
            }

            Barcode.TYPE_WIFI -> {
                barcode.jsonDetails?.let {
                    runCatching {
                        moshi.adapter(QRCodeWifi::class.java).fromJson(it)?.let {
                            _qrCodeActionState.tryEmit(QRCodeAction.CopyText(it.pass.orEmpty()))
                        }
                    }.onFailure {
                        _qrCodeActionState.tryEmit(QRCodeAction.CopyText(barcode.rawData))
                    }
                } ?: run {
                    _qrCodeActionState.tryEmit(QRCodeAction.CopyText(barcode.rawData))
                }
            }

            Barcode.TYPE_CONTACT_INFO -> {
                barcode.jsonDetails?.let {
                    runCatching {
                        moshi.adapter(QRCodeContact::class.java).fromJson(it)?.let {
                            _qrCodeActionState.tryEmit(QRCodeAction.ContactInfo(it))
                        }
                    }.onFailure {
                        _qrCodeActionState.tryEmit(QRCodeAction.CopyText(barcode.rawData))
                    }
                } ?: run {
                    _qrCodeActionState.tryEmit(QRCodeAction.CopyText(barcode.rawData))
                }
            }

            Barcode.TYPE_PHONE -> {
                barcode.jsonDetails?.let {
                    runCatching {
                        moshi.adapter(QRCodePhone::class.java).fromJson(it)?.let {
                            _qrCodeActionState.tryEmit(QRCodeAction.CallPhoneAction(it.number.orEmpty()))
                        }
                    }.onFailure {
                        _qrCodeActionState.tryEmit(QRCodeAction.CopyText(barcode.rawData))
                    }
                } ?: run {
                    _qrCodeActionState.tryEmit(QRCodeAction.CopyText(barcode.rawData))
                }
            }

            Barcode.TYPE_SMS -> {
                barcode.jsonDetails?.let {
                    runCatching {
                        moshi.adapter(QRCodeSMS::class.java).fromJson(it)?.let {
                            _qrCodeActionState.tryEmit(QRCodeAction.SendSMSAction(it))
                        }
                    }.onFailure {
                        _qrCodeActionState.tryEmit(QRCodeAction.CopyText(barcode.rawData))
                    }
                } ?: run {
                    _qrCodeActionState.tryEmit(QRCodeAction.CopyText(barcode.rawData))
                }
            }

            else -> {
                _qrCodeActionState.tryEmit(QRCodeAction.TextSearchGoogle(barcode.rawData))
            }
        }
    }

    fun handleCopyText(text: String) {
        _qrCodeActionState.tryEmit(QRCodeAction.CopyText(text))
    }

    fun handleShareText(text: String) {
        _qrCodeActionState.tryEmit(QRCodeAction.TextShareAction(text))
    }

    fun handleGalleryUri(uri: Uri?) {
        _qrCodeActionState.tryEmit(QRCodeAction.PickGalleryImage(uri ?: return))
    }

    fun showLoading() {
        _mainUiState.value = _mainUiState.value.copy(isLoading = true)
    }

    fun hideLoading() {
        _mainUiState.value = _mainUiState.value.copy(isLoading = false)
    }
}