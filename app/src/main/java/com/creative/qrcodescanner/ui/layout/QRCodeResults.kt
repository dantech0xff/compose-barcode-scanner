package com.creative.qrcodescanner.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.creative.qrcodescanner.AppNavigation
import com.creative.qrcodescanner.LauncherViewModel

@Composable
fun QRCodeResults(modifier: Modifier = Modifier, vm: LauncherViewModel, appNav: AppNavigation) {
    val qrCodeResult = vm.qrCodeResultState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Blue, shape = RectangleShape)
                    .padding(16.dp)
            ) {
                Text(text = "QR Code Results", modifier = Modifier.align(Alignment.Center), color = Color.White)
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
                    .background(Color.Blue, shape = RoundedCornerShape(8.dp))
                    .padding(24.dp, 18.dp)
            ) {
                Text(text = "Continue to Scan", modifier = Modifier.align(Alignment.Center), color = Color.White)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
                .shadow(2.dp, shape = RoundedCornerShape(1.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .wrapContentHeight()
            ) {
                Text(text = "Text")
                Text(text = "2023/12/31 12:00")
            }

            Text(
                text = qrCodeResult.value?.rawValue.orEmpty(),
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .heightIn(64.dp, Dp.Unspecified)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(0.dp, 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "COPY")
                Text(text = "Beautify QR Code")
                Text(text = "Share")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(text = "RAW QR CODE", modifier = Modifier
                    .align(Alignment.Center)
                    .padding(0.dp, 12.dp))
            }
        }
    }
}

@Preview
@Composable
fun QRCodeResultsPreview() {
    QRCodeResults(vm = LauncherViewModel(), appNav = object : AppNavigation {
        override fun openHome() {}
        override fun openFlashLight() {}
        override fun openSetting() {}
        override fun openPremium() {}
        override fun openGallery() {}
        override fun openHistory() {}
    })
}