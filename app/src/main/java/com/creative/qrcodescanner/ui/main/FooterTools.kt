package com.creative.qrcodescanner.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.AppScreen
import com.creative.qrcodescanner.ui.shadow
import com.creative.qrcodescanner.ui.theme.QRCodeScannerTheme

@Composable
fun BoxScope.FooterTools(appNav: NavHostController, pickGallery: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.add_photo_alternate),
        contentDescription = "Gallery Picker",
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .padding(16.dp)
            .shadow(Color(0x901c1c1c).copy(alpha = 0.5f), blurRadius = 12.dp, borderRadius = 32.dp, spread = 0.dp, offsetY = 1.dp, offsetX = 1.dp)
            .align(Alignment.BottomStart)
            .size(64.dp)
            .background(color = Color(0x901c1c1c), shape = CircleShape)
            .clip(CircleShape)
            .clickable {
                pickGallery.invoke()
            }
            .padding(18.dp)
    )

    Image(
        painter = painterResource(id = R.drawable.history),
        contentDescription = "History Picker",
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)
            .shadow(Color(0x901c1c1c).copy(alpha = 0.5f), blurRadius = 12.dp, borderRadius = 32.dp, spread = 0.dp, offsetY = 1.dp, offsetX = 1.dp)

            .size(64.dp)
            .background(color = Color(0x901c1c1c), shape = CircleShape)
            .clip(CircleShape)
            .clickable {
                appNav.navigate(AppScreen.HISTORY.value)
            }
            .padding(18.dp)
    )
}

@Preview
@Composable
fun FooterToolsPreview() {
    QRCodeScannerTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            FooterTools(appNav = rememberNavController(), pickGallery = {})
        }
    }
}