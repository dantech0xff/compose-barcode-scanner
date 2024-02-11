package com.creative.qrcodescanner.ui.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.data.entity.UserSettingData
import com.creative.qrcodescanner.ui.shadow
import com.creative.qrcodescanner.ui.theme.QRCodeScannerTheme

val topIconSize = 42.dp
val topIconPadding = 4.dp
val roundCorner = 4.dp
@Composable
@Stable
fun BoxScope.TopTools(mainUIState: MainUIState,
                      userSettingData: UserSettingData? = null,
                      toggleTorch: (() -> Unit)? = null,
                      toggleCamera: (() -> Unit)? = null,
                      navSetting: (() -> Unit)? = null, navPremium: (() -> Unit)? = null) {

    var isTorchOn by remember { mutableStateOf(false) }
    isTorchOn = mainUIState.isTorchOn
    var isFrontCamera by remember { mutableStateOf(false) }
    isFrontCamera = mainUIState.isFrontCamera

    Row(
        modifier = Modifier
            .align(Alignment.TopCenter)
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

        ToolIcon(icon = if (isTorchOn) R.drawable.flash_off else R.drawable.flash_on,
            contentDescription = stringResource(R.string.flash_light_button),
            clickable = !isFrontCamera,
            onClick = {
                if (isFrontCamera) {
                    return@ToolIcon
                }
                toggleTorch?.invoke()
            }
        )

        ToolIcon(icon = R.drawable.cameraswitch, contentDescription = stringResource(R.string.switch_button)) {
            toggleCamera?.invoke()
        }

        ToolIcon(icon = R.drawable.widgets, contentDescription = stringResource(R.string.setting_button)) {
            navSetting?.invoke()
        }

        if (userSettingData?.isPremium != true) {
            ToolIcon(icon = R.drawable.verified, contentDescription = stringResource(R.string.premium_button)) {
                navPremium?.invoke()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TopToolsPreview() {
    QRCodeScannerTheme {
        Box {
            TopTools(
                MainUIState(
                    isFrontCamera = false,
                    isTorchOn = false,
                    isQRCodeFound = false
                ), UserSettingData(
                    isEnableSound = true,
                    isEnableVibrate = true,
                    isPremium = false,
                ), null, null, null
            )
        }
    }
}

@Composable
@Stable
fun ToolIcon(
    icon: Int,
    contentDescription: String,
    clickable: Boolean = true,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = icon),
        contentDescription = contentDescription,
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .background(color = Color.Transparent)
            .clip(RoundedCornerShape(roundCorner))
            .clickable(clickable, onClick = onClick)
            .size(topIconSize)
            .padding(topIconPadding),
        alpha = if (clickable) 1f else 0.5f
    )
}