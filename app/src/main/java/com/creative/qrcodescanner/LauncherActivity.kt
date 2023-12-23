package com.creative.qrcodescanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creative.qrcodescanner.ui.theme.QRCodeScannerTheme

class LauncherActivity : ComponentActivity() {

    private val launcherViewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            QRCodeScannerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Box(Modifier.safeDrawingPadding()) {
                        QRApp(vm = launcherViewModel,
                            appNav = launcherViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun QRApp(vm: LauncherViewModel, appNav: AppNavigation) {
    Box(modifier = Modifier.fillMaxSize()) {
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
                modifier = Modifier.size(32.dp).clickable {
                    appNav.openHome()
                }
            )

            Image(
                painter = painterResource(id = R.drawable.flash_off),
                contentDescription = "Flash Light Button",
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(32.dp).clickable {
                    appNav.openFlashLight()
                }
            )

            Image(
                painter = painterResource(id = R.drawable.widgets),
                contentDescription = "Setting Button",
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(32.dp).clickable {
                    appNav.openSetting()
                }
            )

            Image(
                painter = painterResource(id = R.drawable.verified),
                contentDescription = "Premium Button",
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(32.dp).clickable {
                    appNav.openPremium()
                }
            )
        }

        Image(
            painter = painterResource(id = R.drawable.add_photo_alternate),
            contentDescription = "Gallery Picker",
            contentScale = ContentScale.Inside,
            modifier = Modifier.align(Alignment.BottomStart)
                .padding(16.dp)
                .size(64.dp)
                .background(color = Color(0x901c1c1c), shape = CircleShape).padding(12.dp).clickable {
                    appNav.openGallery()
                }
        )

        Image(
            painter = painterResource(id = R.drawable.history),
            contentDescription = "History Picker",
            contentScale = ContentScale.Inside,
            modifier = Modifier.align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(64.dp)
                .background(color = Color(0x901c1c1c), shape = CircleShape).padding(12.dp).clickable {
                    appNav.openHistory()
                }
        )
    }
}