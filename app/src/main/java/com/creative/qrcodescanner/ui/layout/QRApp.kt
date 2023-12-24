package com.creative.qrcodescanner.ui.layout

import android.Manifest
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.creative.qrcodescanner.AppNavigation
import com.creative.qrcodescanner.LauncherViewModel
import com.creative.qrcodescanner.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRApp(vm: LauncherViewModel, appNav: AppNavigation) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val context = LocalContext.current
    val cameraController: LifecycleCameraController = remember { LifecycleCameraController(context) }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    var isCameraSwitched = remember {
        false
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)) {
            if (cameraPermissionState.status.isGranted) {
                CameraView(cameraController, lifecycleOwner)
            } else {
                Text(text = "Permission Not Granted")
            }
        }
        Box(
            Modifier
                .safeDrawingPadding()
                .fillMaxSize()
                .background(Color.Transparent)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(color = Color(0x901c1c1c), shape = RoundedCornerShape(24.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home Button",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            appNav.openHome()
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.flash_off),
                    contentDescription = "Flash Light Button",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            appNav.openFlashLight()
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.widgets),
                    contentDescription = "Setting Button",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            appNav.openSetting()
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.cameraswitch),
                    contentDescription = "Switch Button",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            if (isCameraSwitched) {
                                cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                            } else {
                                cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                            }
                            isCameraSwitched = !isCameraSwitched
                        }
                )
            }

            Image(
                painter = painterResource(id = R.drawable.add_photo_alternate),
                contentDescription = "Gallery Picker",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .size(64.dp)
                    .background(color = Color(0x901c1c1c), shape = CircleShape)
                    .padding(12.dp)
                    .clickable {
                        appNav.openGallery()
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.history),
                contentDescription = "History Picker",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(64.dp)
                    .background(color = Color(0x901c1c1c), shape = CircleShape)
                    .padding(12.dp)
                    .clickable {
                        appNav.openHistory()
                    }
            )
        }

        // Home Screen Here

        // Setting Screen Here

        // Premium Screen Here

        // Gallery Screen Here

        // History Screen Here
    }
}