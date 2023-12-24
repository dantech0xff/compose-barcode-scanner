package com.creative.qrcodescanner

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LauncherViewModel : ViewModel(), AppNavigation {

    private val _enableTorchState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val enableTorchState = _enableTorchState.asStateFlow()

    private val _isFrontCameraState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFrontCameraState = _isFrontCameraState.asStateFlow()

    private val _isScanQRSuccessState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isScanQRSuccessState = _isScanQRSuccessState.asStateFlow()

    fun toggleTorch() {
        _enableTorchState.value = !_enableTorchState.value
    }

    fun scanQRSuccess() {
        _isScanQRSuccessState.value = true
    }

    fun resetScanQR() {
        _isScanQRSuccessState.value = false
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