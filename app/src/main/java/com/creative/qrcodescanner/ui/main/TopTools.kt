package com.creative.qrcodescanner.ui.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.data.entity.UserSettingData
import com.creative.qrcodescanner.ui.AppScreen
import com.creative.qrcodescanner.ui.shadow
import com.creative.qrcodescanner.ui.theme.QRCodeScannerTheme

val topIconSize = 42.dp
val topIconPadding = 4.dp
val roundCorner = 4.dp
@Composable
@Stable
fun TopTools(modifier: Modifier, mainUIState: MainUIState,
             userSettingData: UserSettingData? = null,
             appNav: NavHostController,
             cameraController: ICameraController? = null   ) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .shadow(Color(0x901c1c1c).copy(alpha = 0.5f), blurRadius = 10.dp, borderRadius = 32.dp, spread = 0.dp, offsetY = 1.dp, offsetX = 1.dp)
            .wrapContentWidth()
            .wrapContentHeight()
            .background(color = Color(0x901c1c1c), shape = RoundedCornerShape(32.dp))
            .padding(24.dp, 6.dp)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            painter = painterResource(id = if (mainUIState.isEnableTorch) R.drawable.flash_on else R.drawable.flash_off),
            contentDescription = "Flash Light Button",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .background(color = Color.Transparent)
                .clip(RoundedCornerShape(roundCorner))
                .clickable(!mainUIState.isFrontCamera) {
                    if (mainUIState.isFrontCamera) {
                        return@clickable
                    }
                    cameraController?.toggleTorch()
                }
                .size(topIconSize)
                .padding(topIconPadding),
            alpha = if (mainUIState.isFrontCamera) 0.5f else 1f)

        Image(
            painter = painterResource(id = R.drawable.cameraswitch),
            contentDescription = "Switch Button",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .background(color = Color.Transparent)
                .clip(RoundedCornerShape(roundCorner))
                .clickable {
                    cameraController?.toggleCamera()
                }
                .size(topIconSize)
                .padding(topIconPadding))

        Image(
            painter = painterResource(id = R.drawable.widgets),
            contentDescription = "Setting Button",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .background(color = Color.Transparent)
                .clip(RoundedCornerShape(roundCorner))
                .clickable {
                    appNav.navigate(AppScreen.SETTING.value)
                }
                .size(topIconSize)
                .padding(topIconPadding))

        if (userSettingData?.isPremium != true) {
            Image(
                painter = painterResource(id = R.drawable.verified),
                contentDescription = "Setting Button",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .clip(RoundedCornerShape(roundCorner))
                    .clickable {
                        appNav.navigate(AppScreen.PREMIUM.value)
                    }
                    .size(topIconSize)
                    .padding(topIconPadding)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TopToolsPreview() {
    QRCodeScannerTheme {
        TopTools(
            Modifier, MainUIState(
                isFrontCamera = false,
                isEnableTorch = false,
                isQRCodeFound = false
            ), UserSettingData(
                isEnableSound = true,
                isEnableVibrate = true,
                isPremium = false,
            ),
            rememberNavController(), null
        )
    }
}