package com.creative.qrcodescanner.ui.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.qrcodescanner.data.entity.QRCodeContact
import com.creative.qrcodescanner.data.entity.QRCodePhone
import com.creative.qrcodescanner.data.entity.QRCodeSMS
import com.creative.qrcodescanner.data.entity.QRCodeURL
import com.creative.qrcodescanner.data.entity.QRCodeWifi
import com.creative.qrcodescanner.data.entity.toQRCodeEntity
import com.creative.qrcodescanner.repo.user.UserDataRepo
import com.creative.qrcodescanner.ui.result.QRCodeRawData
import com.creative.qrcodescanner.usecase.InsertQRCodeHistoryFlowUseCase
import com.google.mlkit.vision.barcode.common.Barcode
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
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

data class MainUIState(
    val isFrontCamera: Boolean = false,
    val isEnableTorch: Boolean = false,
    val isLoading: Boolean = false,
    val isQRCodeFound: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val insertQRCodeHistoryUseCase: InsertQRCodeHistoryFlowUseCase,
    private val moshi: Moshi,
    userDataRepo: UserDataRepo
) : ViewModel() {

    companion object {
        const val INVALID_DB_ROW_ID = 0
    }

    val appSettingState = userDataRepo.userSettingData.stateIn(viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = null)

    private val _mainUiState: MutableStateFlow<MainUIState> = MutableStateFlow(MainUIState())
    val mainUiState: StateFlow<MainUIState> = _mainUiState.asStateFlow()

    private val _databaseIdOfQRCodeState: MutableSharedFlow<Int> = MutableSharedFlow(INVALID_DB_ROW_ID)
    val databaseIdOfQRCodeState = _databaseIdOfQRCodeState.asSharedFlow()

    private val _openUrlSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val openUrlState = _openUrlSharedFlow.asSharedFlow()

    private val _copyTextSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val copyTextState = _copyTextSharedFlow.asSharedFlow()

    private val _contactInfoSharedFlow: MutableSharedFlow<QRCodeContact> = MutableSharedFlow(extraBufferCapacity = 1)
    val contactInfoState = _contactInfoSharedFlow.asSharedFlow()

    private val _textSearchGoogleSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val textSearchGoogleState = _textSearchGoogleSharedFlow.asSharedFlow()

    private val _textShareActionSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val textShareActionState = _textShareActionSharedFlow.asSharedFlow()

    private val _sendSMSActionSharedFlow: MutableSharedFlow<QRCodeSMS> = MutableSharedFlow(extraBufferCapacity = 1)
    val sendSMSActionState = _sendSMSActionSharedFlow.asSharedFlow()

    private val _callPhoneActionSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val callPhoneActionState = _callPhoneActionSharedFlow.asSharedFlow()

    private val _galleryUriSharedFlow: MutableSharedFlow<Uri?> = MutableSharedFlow(extraBufferCapacity = 1)
    val galleryUriState = _galleryUriSharedFlow.asSharedFlow()

    fun toggleTorch() {
        _mainUiState.value = _mainUiState.value.let {
            it.copy(isEnableTorch = !it.isEnableTorch)
        }
    }

    fun scanQRSuccess(result: Barcode) {
        if(mainUiState.value.isQRCodeFound){
            return
        }
        _mainUiState.value = _mainUiState.value.copy(isQRCodeFound = true)

        viewModelScope.launch {
            insertQRCodeHistoryUseCase.execute(
                input = result.toQRCodeEntity()
            ).collectLatest {
                _databaseIdOfQRCodeState.tryEmit(it.toInt())
            }
        }
    }

    fun resetScanQR() {
        _mainUiState.value = _mainUiState.value.copy(isQRCodeFound = false)
        _databaseIdOfQRCodeState.tryEmit(INVALID_DB_ROW_ID)
    }

    fun toggleCamera() {
        _mainUiState.value = _mainUiState.value.let {
            it.copy(isFrontCamera = !it.isFrontCamera)
        }
    }

    fun handleBarcodeResult(barcode: QRCodeRawData) {
        when (barcode.type) {
            Barcode.TYPE_URL -> {
                barcode.jsonDetails?.let {
                    runCatching {
                        moshi.adapter(QRCodeURL::class.java).fromJson(it)?.let {
                            val tryEmit = _openUrlSharedFlow.tryEmit(it.url.orEmpty())
                            Log.d("QRApp", "openUrlState: tryEmit $tryEmit")
                        } ?: throw Exception("QRCodeURL is null")
                    }.onFailure {
                        _copyTextSharedFlow.tryEmit(barcode.rawData)
                    }
                } ?: run {
                    _copyTextSharedFlow.tryEmit(barcode.rawData)
                }
            }

            Barcode.TYPE_WIFI -> {
                barcode.jsonDetails?.let {
                    runCatching {
                        moshi.adapter(QRCodeWifi::class.java).fromJson(it)?.let {
                            val tryEmit = _openUrlSharedFlow.tryEmit(it.pass.orEmpty())
                            Log.d("QRApp", "openUrlState: tryEmit $tryEmit")
                        }
                    }.onFailure {
                        _copyTextSharedFlow.tryEmit(barcode.rawData)
                    }
                } ?: run {
                    _copyTextSharedFlow.tryEmit(barcode.rawData)
                }
            }

            Barcode.TYPE_CONTACT_INFO -> {
                barcode.jsonDetails?.let {
                    runCatching {
                        moshi.adapter(QRCodeContact::class.java).fromJson(it)?.let {
                            val tryEmit = _contactInfoSharedFlow.tryEmit(it)
                            Log.d("QRApp", "contactInfoState: tryEmit $tryEmit")
                        }
                    }.onFailure {
                        _copyTextSharedFlow.tryEmit(barcode.rawData)
                    }
                } ?: run {
                    _copyTextSharedFlow.tryEmit(barcode.rawData)
                }
            }

            Barcode.TYPE_PHONE -> {
                barcode.jsonDetails?.let {
                    runCatching {
                        moshi.adapter(QRCodePhone::class.java).fromJson(it)?.let {
                            val tryEmit = _callPhoneActionSharedFlow.tryEmit(it.number.orEmpty())
                            Log.d("QRApp", "openUrlState: tryEmit $tryEmit")
                        }
                    }.onFailure {
                        _copyTextSharedFlow.tryEmit(barcode.rawData)
                    }
                } ?: run {
                    _copyTextSharedFlow.tryEmit(barcode.rawData)
                }
            }

            Barcode.TYPE_SMS -> {
                barcode.jsonDetails?.let {
                    runCatching {
                        moshi.adapter(QRCodeSMS::class.java).fromJson(it)?.let {
                            val tryEmit = _sendSMSActionSharedFlow.tryEmit(it)
                            Log.d("QRApp", "openUrlState: tryEmit $tryEmit")
                        }
                    }.onFailure {
                        _copyTextSharedFlow.tryEmit(barcode.rawData)
                    }
                } ?: run {
                    _copyTextSharedFlow.tryEmit(barcode.rawData)
                }
            }

            else -> {
                _textSearchGoogleSharedFlow.tryEmit(barcode.rawData)
            }
        }
    }

    fun handleCopyText(text: String) {
        val tryEmit = _copyTextSharedFlow.tryEmit(text)
        Log.d("QRApp", "copyTextState: tryEmit $tryEmit")
    }

    fun handleShareText(text: String) {
        val tryEmit = _textShareActionSharedFlow.tryEmit(text)
        Log.d("QRApp", "_textShareActionSharedFlow: tryEmit $tryEmit")
    }

    fun handleGalleryUri(uri: Uri?) {
        val tryEmit = _galleryUriSharedFlow.tryEmit(uri)
        Log.d("QRApp", "_galleryUriSharedFlow: tryEmit $tryEmit")
    }

    fun showLoading() {
        _mainUiState.value = _mainUiState.value.copy(isLoading = true)
    }
    fun hideLoading() {
        _mainUiState.value = _mainUiState.value.copy(isLoading = false)
    }
}