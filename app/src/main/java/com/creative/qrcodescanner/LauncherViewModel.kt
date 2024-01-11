package com.creative.qrcodescanner

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.creative.qrcodescanner.data.QRAppDatabase
import com.creative.qrcodescanner.ui.result.QRCodeRawData
import com.creative.qrcodescanner.ui.result.toQRCodeRawData
import com.google.mlkit.vision.barcode.common.Barcode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor() : ViewModel() {

    private val _enableTorchState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val enableTorchState = _enableTorchState.asStateFlow()

    private val _isFrontCameraState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFrontCameraState = _isFrontCameraState.asStateFlow()

    private val _qrCodeResultState: MutableStateFlow<QRCodeRawData?> = MutableStateFlow(null)
    val qrCodeResultState = _qrCodeResultState.asStateFlow().distinctUntilChanged { old, new ->
        old?.rawData == new?.rawData
    }

    private val _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    private val _openUrlSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val openUrlState = _openUrlSharedFlow.asSharedFlow()

    private val _copyTextSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val copyTextState = _copyTextSharedFlow.asSharedFlow()

    private val _contactInfoSharedFlow: MutableSharedFlow<Barcode.ContactInfo> = MutableSharedFlow(extraBufferCapacity = 1)
    val contactInfoState = _contactInfoSharedFlow.asSharedFlow()

    private val _textSearchGoogleSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val textSearchGoogleState = _textSearchGoogleSharedFlow.asSharedFlow()

    private val _textShareActionSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val textShareActionState = _textShareActionSharedFlow.asSharedFlow()

    private val _galleryUriSharedFlow: MutableSharedFlow<Uri?> = MutableSharedFlow(extraBufferCapacity = 1)
    val galleryUriState = _galleryUriSharedFlow.asSharedFlow()

    fun toggleTorch() {
        _enableTorchState.value = !_enableTorchState.value
    }

    fun scanQRSuccess(result: Barcode) {
        _qrCodeResultState.value = result.toQRCodeRawData()
    }

    fun resetScanQR() {
        _qrCodeResultState.value = null
    }

    fun toggleCamera() {
        _isFrontCameraState.value = !_isFrontCameraState.value
    }

    fun handleBarcodeResult(barcode: Barcode) {
        when (barcode.valueType) {
            Barcode.TYPE_URL -> {
                val url = barcode.url?.url.orEmpty()
                val tryEmit = _openUrlSharedFlow.tryEmit(url)
                Log.d("QRApp", "openUrlState: tryEmit $tryEmit")
            }

            Barcode.TYPE_WIFI -> {
                val pass = barcode.wifi?.password.orEmpty()
                val tryEmit = _copyTextSharedFlow.tryEmit(pass)
                Log.d("QRApp", "copyTextState: tryEmit $tryEmit")
            }

            Barcode.TYPE_CONTACT_INFO -> {
                // Handle add contact info
                val contact = barcode.contactInfo
                if(contact != null) {
                    val tryEmit = _contactInfoSharedFlow.tryEmit(contact)
                    Log.d("QRApp", "contactInfoState: tryEmit $tryEmit")
                }
            }

            Barcode.TYPE_CALENDAR_EVENT -> {
                // Handle add calendar event
            }

            Barcode.TYPE_EMAIL -> {
                // handle send email
            }

            Barcode.TYPE_GEO, Barcode.TYPE_TEXT, Barcode.TYPE_DRIVER_LICENSE, Barcode.TYPE_PRODUCT, Barcode.TYPE_ISBN, Barcode.TYPE_UNKNOWN -> {
                // Handle Search Text in Google
                val text = barcode.displayValue.orEmpty()
                val tryEmit = _textSearchGoogleSharedFlow.tryEmit(text)
                Log.d("QRApp", "textSearchGoogleState: tryEmit $tryEmit")
            }

            Barcode.TYPE_PHONE -> {
                // Handle call phone
            }

            Barcode.TYPE_SMS -> {
                // Handle send sms
            }

            else -> {
                // Handle Search Text in Google
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
        _loadingState.value = true
    }
    fun hideLoading() {
        _loadingState.value = false
    }

    fun isEnableVibrate() = true
}