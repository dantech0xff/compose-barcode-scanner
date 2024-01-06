package com.creative.qrcodescanner.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
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
import androidx.navigation.NavHostController
import com.creative.qrcodescanner.AppNavigation
import com.creative.qrcodescanner.R

@Composable
fun BoxScope.FooterTools(appNav: NavHostController) {
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
                appNav.navigate(AppScreen.GALLERY.value)
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
                appNav.navigate(AppScreen.HISTORY.value)
            }
    )
}