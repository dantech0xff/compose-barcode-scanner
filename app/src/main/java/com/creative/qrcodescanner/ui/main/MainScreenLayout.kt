package com.creative.qrcodescanner.ui.main

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.creative.qrcodescanner.ui.AppScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.collectLatest


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

    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }.apply {
        bindToLifecycle(LocalLifecycleOwner.current)
    }

    val galleryPicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult = {
        vm.handleGalleryUri(it)
    })

    LaunchedEffect(key1 = Unit) {
        vm.galleryUriState.collectLatest { uri ->
            if (uri != null) {
                vm.showLoading()
                BarcodeScanning.getClient().process(InputImage.fromFilePath(context, uri))
                    .addOnSuccessListener { barcodes ->
                        vm.hideLoading()
                        if (barcodes.isEmpty()) {
                            return@addOnSuccessListener
                        }
                        barcodes.forEach { barcode ->
                            if (barcode.rawValue != null) {
                                vm.scanQRSuccess(barcode)
                            }
                        }
                    }
                    .addOnFailureListener {
                        vm.hideLoading()
                    }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.isFrontCameraState.collectLatest {
            cameraController.cameraSelector = if (it) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.enableTorchState.collectLatest {
            cameraController.enableTorch(it)
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.databaseIdOfQRCodeState.collectLatest {
            if (it != LauncherViewModel.INVALID_DB_ROW_ID) {
                if (vm.isEnableVibrate()) {
                    context.vibrate(200L)
                }
                appNavHost.navigate(
                    AppScreen.RESULT.value.replace(
                        "{id}",
                        it.toString()
                    )
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.qrCodeResultFoundState.collectLatest { qrCodeResult ->
            if (qrCodeResult) {
                cameraController.clearImageAnalysisAnalyzer()
            } else {
                cameraController.setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                    imageProxy.image?.let {
                        InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
                            .let { image ->
                                val scanner = BarcodeScanning.getClient()
                                scanner.process(image)
                                    .addOnSuccessListener { barcodes ->
                                        if (barcodes.isEmpty()) {
                                            return@addOnSuccessListener
                                        }
                                        barcodes.forEach { barcode ->
                                            if (barcode.rawValue != null) {
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
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (cameraPermissionState.status.isGranted) {
                CameraView(cameraController)
            } else {
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

            FooterTools(appNavHost, pickGallery = {
                galleryPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            })
        }

        AnimatedVisibility(visible = vm.loadingState.collectAsStateWithLifecycle().value, enter = fadeIn(), exit = fadeOut()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color(0x901c1c1c))
                .clickable { }) {
                Text(
                    text = stringResource(R.string.loading_qr_code_scanner_engine),
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}

fun Context.vibrate(milliseconds: Long) {
    val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v?.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        v?.vibrate(milliseconds)
    }
}