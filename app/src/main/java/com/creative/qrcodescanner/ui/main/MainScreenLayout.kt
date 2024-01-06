package com.creative.qrcodescanner.ui.main

import android.Manifest
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creative.qrcodescanner.LauncherViewModel
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.layout.CameraView
import com.creative.qrcodescanner.ui.layout.FooterTools
import com.creative.qrcodescanner.ui.layout.QRCodeResults
import com.creative.qrcodescanner.ui.layout.TopTools
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

/**
 * Created by dan on 07/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */
 
@androidx.annotation.OptIn(ExperimentalGetImage::class)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreenLayout(vm: LauncherViewModel, appNavHost: NavHostController) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val isFrontCamera by vm.isFrontCameraState.collectAsStateWithLifecycle()
    val enableTorch by vm.enableTorchState.collectAsStateWithLifecycle()
    val qrCodeResult by vm.qrCodeResultState.collectAsStateWithLifecycle()

    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }.apply {
        bindToLifecycle(LocalLifecycleOwner.current)
        cameraSelector = if (isFrontCamera) {
            CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            CameraSelector.DEFAULT_BACK_CAMERA
        }
        enableTorch(enableTorch)
        if (qrCodeResult != null) {
            Log.d("QRAppResult", "clearImageAnalysisAnalyzer")
            clearImageAnalysisAnalyzer()
        } else {
            setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                imageProxy.image?.let {
                    InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
                        .let { image ->
                            val scanner = BarcodeScanning.getClient()
                            scanner.process(image)
                                .addOnSuccessListener { barcodes ->
                                    barcodes.forEach { barcode ->
                                        if (barcode.rawValue != null) {
                                            Log.d("QRAppResult", "${barcode.rawValue}")
                                            vm.scanQRSuccess(barcode)
                                        }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    exception.printStackTrace()
                                }
                                .addOnCompleteListener {
                                    imageProxy.close()
                                }
                        }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (cameraPermissionState.status.isGranted && qrCodeResult == null) {
                CameraView(cameraController)
            } else if (!cameraPermissionState.status.isGranted) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = stringResource(R.string.camera_permission_is_not_granted))
                    Button(onClick = {
                        cameraPermissionState.launchPermissionRequest()
                    }) {
                        Text(text = stringResource(R.string.click_to_grant_camera_permission))
                    }
                }
            }
        }

        Box(
            Modifier
                .safeDrawingPadding()
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            TopTools(
                Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(color = Color(0x901c1c1c), shape = RoundedCornerShape(32.dp))
                    .padding(24.dp, 6.dp)
                    .animateContentSize(),
                appNavHost, vm
            )

            FooterTools(appNavHost)
        }

        AnimatedVisibility(
            visible = qrCodeResult != null,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            Box(
                modifier = Modifier
                    .safeDrawingPadding()
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                QRCodeResults(data = qrCodeResult, dismiss = {
                    vm.resetScanQR()
                })
            }
        }
    }
}