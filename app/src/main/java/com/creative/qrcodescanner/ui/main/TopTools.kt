package com.creative.qrcodescanner.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creative.qrcodescanner.LauncherViewModel
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.AppScreen

val topIconSize = 38.dp
val topIconPadding = 4.dp

@Composable
fun TopTools(modifier: Modifier, appNav: NavHostController, vm: LauncherViewModel) {

    val isFrontCamera = vm.isFrontCameraState.collectAsStateWithLifecycle()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.flash_off),
            contentDescription = "Flash Light Button",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .background(color = Color.Transparent, shape = CircleShape)
                .clickable(!isFrontCamera.value) {
                    if (isFrontCamera.value) {
                        return@clickable
                    }
                    vm.toggleTorch()
                }
                .size(topIconSize)
                .padding(topIconPadding),
            alpha = if (isFrontCamera.value) 0.5f else 1f
        )

        Image(
            painter = painterResource(id = R.drawable.cameraswitch),
            contentDescription = "Switch Button",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .background(color = Color.Transparent, shape = CircleShape)
                .clickable {
                    vm.toggleCamera()
                }
                .size(topIconSize)
                .padding(topIconPadding)
        )

        Image(
            painter = painterResource(id = R.drawable.widgets),
            contentDescription = "Setting Button",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .background(color = Color.Transparent, shape = CircleShape)
                .clickable {
                    appNav.navigate(AppScreen.SETTING.value)
                }
                .size(topIconSize)
                .padding(topIconPadding)

        )
    }
}