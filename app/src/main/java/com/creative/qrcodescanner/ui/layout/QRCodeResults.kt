package com.creative.qrcodescanner.ui.layout

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.creative.qrcodescanner.AppNavigation
import com.creative.qrcodescanner.R
import com.google.mlkit.vision.barcode.common.Barcode

@Composable
fun QRCodeResults(data: QRCodeRawData?, dismiss: (() -> Unit)) {
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
                    .padding(24.dp, 18.dp).clickable {
                        dismiss()
                    }
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
                .padding(8.dp)
                .shadow(1.dp, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "QR Code Type",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(text = data?.typeString.orEmpty())
                    Text(text = data?.scanDate.orEmpty())
                }
            }

            Text(
                text = data?.rawData.orEmpty(),
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp)
                    .heightIn(64.dp, Dp.Unspecified)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                    .padding(4.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp)
                    .background(Color.Blue, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Open in Browser", modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center, color = Color.White
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(0.dp, 6.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(Color.Blue, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "COPY", modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center, color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(Color.Blue, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "SHARE", modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center, color = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    bitmap = (data?.qrCodeBitmap ?: Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888)).asImageBitmap(),
                    contentDescription = "QR",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(vertical = 8.dp).size(256.dp).clip(RoundedCornerShape(8.dp))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.Blue, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Beautify QR Code", modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center, color = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun QRCodeResultsPreview() {
    QRCodeResults(data = QRCodeRawData(
        typeString = "TEXT",
        scanDate = "2023/12/31 12:00",
        rawData = "https://www.google.com",
        qrCodeBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888)
    ), dismiss = {})
}

data class QRCodeRawData(
    @DrawableRes val typeIcon: Int = 0,
    val qrCodeBitmap: Bitmap,
    val typeString: String,
    val scanDate: String,
    val rawData: String,
)

fun Barcode.toQRCodeRawData(): QRCodeRawData {
    return QRCodeRawData(
        typeIcon = 0,
        qrCodeBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888),
        typeString = this.valueType.toString(),
        scanDate = Calendar.getInstance().time.toString(),
        rawData = this.rawValue.orEmpty()
    )
}