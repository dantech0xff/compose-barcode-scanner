package com.creative.qrcodescanner

import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LauncherViewModel : ViewModel(), AppNavigation {

    private val _enableTorchState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val enableTorchState = _enableTorchState.asStateFlow()

    private val _isFrontCameraState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFrontCameraState = _isFrontCameraState.asStateFlow()

    private val _qrCodeResultState: MutableStateFlow<Barcode?> = MutableStateFlow(null)
    val qrCodeResultState = _qrCodeResultState.asStateFlow()

    fun toggleTorch() {
        _enableTorchState.value = !_enableTorchState.value
    }

    fun scanQRSuccess(result: Barcode) {
        _qrCodeResultState.value = result
    }

    fun resetScanQR() {
        _qrCodeResultState.value = null
    }

    fun toggleCamera() {
        _isFrontCameraState.value = !_isFrontCameraState.value
    }

    override fun openHome() {}

    override fun openFlashLight() {}

    override fun openSetting() {}

    override fun openPremium() {}

    override fun openGallery() {}

    override fun openHistory() {}
}