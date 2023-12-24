package com.creative.qrcodescanner.ui.layout

import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.creative.qrcodescanner.AppNavigation
import com.creative.qrcodescanner.R

val topIconSize = 32.dp
val topIconPadding = 4.dp

@Composable
fun TopTools(modifier: Modifier, appNav: AppNavigation,
             isFrontCamera: MutableState<Boolean>,
             isEnableTorch: MutableState<Boolean>,
             cameraController: LifecycleCameraController) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.home),
            contentDescription = "Home Button",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .background(color = Color.Transparent, shape = CircleShape)
                .size(topIconSize)
                .padding(topIconPadding)
                .clickable {
                    appNav.openHome()
                }
        )

        if (!isFrontCamera.value) {
            Image(
                painter = painterResource(id = R.drawable.flash_off),
                contentDescription = "Flash Light Button",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .background(color = Color.Transparent, shape = CircleShape)
                    .size(topIconSize)
                    .padding(topIconPadding)
                    .clickable {
                        if (isFrontCamera.value) {
                            return@clickable
                        }
                        isEnableTorch.value = !isEnableTorch.value
                        cameraController.enableTorch(isEnableTorch.value)
                    }
            )
        }

        Image(
            painter = painterResource(id = R.drawable.cameraswitch),
            contentDescription = "Switch Button",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .background(color = Color.Transparent, shape = CircleShape)
                .size(topIconSize)
                .padding(topIconPadding)
                .clickable {
                    if (isFrontCamera.value) {
                        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    } else {
                        cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                    }
                    isFrontCamera.value = !isFrontCamera.value
                }
        )

        Image(
            painter = painterResource(id = R.drawable.widgets),
            contentDescription = "Setting Button",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .background(color = Color.Transparent, shape = CircleShape)
                .size(topIconSize)
                .padding(topIconPadding)
                .clickable {
                    appNav.openSetting()
                }
        )
    }
}