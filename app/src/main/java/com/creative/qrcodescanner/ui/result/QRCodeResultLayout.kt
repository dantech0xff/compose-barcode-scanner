package com.creative.qrcodescanner.ui.result

import android.graphics.Bitmap
import android.icu.util.Calendar
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.theme.fontSize
import com.google.mlkit.vision.barcode.common.Barcode

@Composable
fun QRCodeResultLayout(data: QRCodeRawData?, appNav: NavHostController,
                       dismiss: (() -> Unit) = {},
                       callbackHandleQR: ((Barcode?) -> Unit) = {},
                       callbackCopyRawValue: ((String) -> Unit) = {},
                       callbackShareRawValue: ((String) -> Unit) = {}
) {
    BackHandler {
        appNav.popBackStack()
        dismiss.invoke()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary, shape = RectangleShape)
            ) {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

                Text(text = stringResource(R.string.qr_code_result),
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(12.dp),
                    color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyLarge)
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        appNav.popBackStack()
                        dismiss.invoke()
                    }
                    .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 0.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_qr_white),
                        contentDescription = stringResource(id = R.string.continue_to_scan),
                        modifier = Modifier.size(28.dp))
                    Text(text = stringResource(R.string.continue_to_scan), color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = data?.typeIcon ?: R.drawable.icon_qr),
                    contentDescription = stringResource(R.string.qr_code_type),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp)).padding(6.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Text(text = data?.typeStringRes?.let { stringResource(it) }.orEmpty(), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
                    Text(text = data?.scanDate.orEmpty(), fontSize = fontSize.heading8, color = Color.Gray)
                }
            }

            Text(
                text = data?.rawData.orEmpty(),
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxWidth()
                    .heightIn(64.dp, Dp.Unspecified)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
                    .padding(4.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        callbackHandleQR.invoke(data?.barcode)
                    }
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(id = data?.ctaHandleStringRes ?: R.string.copy).uppercase(), modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
                        .clickable {
                            callbackCopyRawValue.invoke(data?.rawData.orEmpty())
                        }
                        .padding(12.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.copy_text).uppercase(), modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
                        .clickable {
                            callbackShareRawValue.invoke(data?.rawData.orEmpty())
                        }
                        .padding(12.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.share_text).uppercase(), modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun QRCodeResultsPreview() {
    QRCodeResultLayout(data = QRCodeRawData(
        typeStringRes = R.string.text,
        scanDate = "2023/12/31 12:00",
        rawData = "https://www.google.com",
        qrCodeBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888),
        ctaHandleStringRes = R.string.text
    ), rememberNavController())
}

data class QRCodeRawData(
    @DrawableRes val typeIcon: Int = 0,
    val qrCodeBitmap: Bitmap,
    @StringRes val typeStringRes: Int = 0,
    val scanDate: String,
    val rawData: String,
    @StringRes val ctaHandleStringRes: Int,
    val barcode: Barcode? = null
)

fun Barcode.toQRCodeRawData(): QRCodeRawData {
    return QRCodeRawData(
        typeIcon = when (valueType) {
            Barcode.TYPE_URL -> R.drawable.icon_link
            Barcode.TYPE_WIFI -> R.drawable.icon_wifi
            Barcode.TYPE_CONTACT_INFO -> R.drawable.icon_contact
            Barcode.TYPE_CALENDAR_EVENT -> R.drawable.icon_calendar_event
            Barcode.TYPE_EMAIL -> R.drawable.icon_email
            Barcode.TYPE_GEO -> R.drawable.icon_geo
            Barcode.TYPE_PHONE -> R.drawable.icon_phone
            Barcode.TYPE_SMS -> R.drawable.icon_sms
            Barcode.TYPE_TEXT -> R.drawable.icon_text
            Barcode.TYPE_DRIVER_LICENSE -> R.drawable.icon_license
            Barcode.TYPE_PRODUCT -> R.drawable.icon_product
            Barcode.TYPE_ISBN -> R.drawable.icon_isbn
            Barcode.TYPE_UNKNOWN -> R.drawable.icon_qr
            else -> R.drawable.icon_qr
        },
        qrCodeBitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888),
        typeStringRes = when (valueType) {
            Barcode.TYPE_URL -> R.string.url
            Barcode.TYPE_WIFI -> R.string.wifi
            Barcode.TYPE_CONTACT_INFO -> R.string.contact
            Barcode.TYPE_CALENDAR_EVENT -> R.string.calendar
            Barcode.TYPE_EMAIL -> R.string.email
            Barcode.TYPE_GEO -> R.string.geo
            Barcode.TYPE_PHONE -> R.string.phone
            Barcode.TYPE_SMS -> R.string.sms
            Barcode.TYPE_TEXT -> R.string.text
            Barcode.TYPE_DRIVER_LICENSE -> R.string.driver_license
            Barcode.TYPE_PRODUCT -> R.string.product
            Barcode.TYPE_ISBN -> R.string.isbn
            Barcode.TYPE_UNKNOWN -> R.string.text
            else -> R.string.text
        },
        scanDate = Calendar.getInstance().time.toString(),
        rawData = this.rawValue.orEmpty(),
        ctaHandleStringRes = when (valueType) {
            Barcode.TYPE_URL -> R.string.open_in_browser
            Barcode.TYPE_WIFI -> R.string.copy_password
            Barcode.TYPE_CONTACT_INFO -> R.string.add_contact
            Barcode.TYPE_CALENDAR_EVENT -> R.string.add_calendar
            Barcode.TYPE_EMAIL -> R.string.send_email
            Barcode.TYPE_GEO -> R.string.search
            Barcode.TYPE_PHONE -> R.string.call
            Barcode.TYPE_SMS -> R.string.send_sms
            Barcode.TYPE_TEXT -> R.string.search
            Barcode.TYPE_DRIVER_LICENSE -> R.string.search
            Barcode.TYPE_PRODUCT -> R.string.search
            Barcode.TYPE_ISBN -> R.string.search
            Barcode.TYPE_UNKNOWN -> R.string.search
            else -> R.string.search
        },
        barcode = this
    )
}