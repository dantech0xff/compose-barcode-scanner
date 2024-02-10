package com.creative.qrcodescanner.ui.main

import android.view.ViewGroup
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.data.entity.UserSettingData
import com.creative.qrcodescanner.ui.theme.seed
import kotlinx.coroutines.delay

val cornerColorIdle = Color.White
val cornerColorActive = seed
val centerColor = Color.Red
const val cornerStrokeLength = 120f
const val cornerStrokeWidth = 20f
val dimmingColor = Color.Black.copy(alpha = 0.3f)

@Composable
fun QRCameraView(lifecycleCameraController: LifecycleCameraController,
                 userSettingData: UserSettingData? = null,
                 handleSwitchKeepScanning: (Boolean) -> Unit = {}) {

    val cornerColorAnimate = remember {
        Animatable(cornerColorIdle)
    }
    val centerLineColorAnimate = remember {
        Animatable(centerColor)
    }
    LaunchedEffect(key1 = userSettingData, block = {
        if(userSettingData?.isKeepScanning == true) {
            cornerColorAnimate.animateTo(cornerColorActive, tween(500))
        } else {
            cornerColorAnimate.animateTo(cornerColorIdle, tween(500))
        }
    })
    LaunchedEffect(key1 = Unit) {
        while (true) {
            if (centerLineColorAnimate.value == centerColor) {
                centerLineColorAnimate.animateTo(Color.Transparent, tween(200))
                delay(250)
            } else {
                centerLineColorAnimate.animateTo(centerColor, tween(300))
                delay(350)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(modifier = Modifier.fillMaxSize(), factory = { ctx ->
            PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(Color.Black.toArgb())
                scaleType = PreviewView.ScaleType.FILL_CENTER
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }.also {
                it.controller = lifecycleCameraController
            }
        })

        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {

            val w = size.width
            val h = size.height
            val squareSize = w.coerceAtMost(h) * 0.75f
            val shiftTop = w / 8

            drawRect(color = dimmingColor, size = Size(w, (h - squareSize) / 2 - shiftTop), topLeft = Offset(x = 0f, y = 0f))
            drawRect(color = dimmingColor, size = Size(w, (h - squareSize) / 2 + shiftTop), topLeft = Offset(x = 0f, y = h - (h - squareSize) / 2 - shiftTop))

            drawRect(color = dimmingColor, size = Size((w - squareSize) / 2, squareSize), topLeft = Offset(x = 0f, y = (h - squareSize) / 2 - shiftTop))
            drawRect(color = dimmingColor, size = Size((w - squareSize) / 2, squareSize), topLeft = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop))

            drawLine(
                color = cornerColorAnimate.value,
                start = Offset(x = (w - squareSize) / 2 , y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2 + cornerStrokeLength, y = (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )
            drawLine(
                color = cornerColorAnimate.value,
                start = Offset(x = (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop + cornerStrokeLength),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColorAnimate.value,
                start = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2 - cornerStrokeLength, y = (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColorAnimate.value,
                start = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop + cornerStrokeLength),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColorAnimate.value,
                start = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2 + cornerStrokeLength, y = h - (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColorAnimate.value,
                start = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop - cornerStrokeLength),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColorAnimate.value,
                start = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2 - cornerStrokeLength, y = h - (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColorAnimate.value,
                start = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop - cornerStrokeLength),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            // draw center line of the square, in horizontal
            drawLine(
                color = centerLineColorAnimate.value,
                start = Offset(x = (w - squareSize) / 2 + 10f, y = h / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2 - 10f, y = h / 2 - shiftTop),
                strokeWidth = 10f,
                cap = StrokeCap.Round
            )
        })

        val width = LocalContext.current.resources.displayMetrics.widthPixels

        if(userSettingData?.isPremium == true) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (width / 8).dp)
            ) {
                Text(text = "Keep Scanning", color = Color.White, style = MaterialTheme.typography.titleMedium)
                Switch(checked = userSettingData.isKeepScanning, onCheckedChange = {
                    handleSwitchKeepScanning.invoke(userSettingData.isKeepScanning)
                }, thumbContent = {
                    Image(
                        painter = painterResource(id = R.drawable.icon_qr),
                        contentDescription = stringResource(id = R.string.app_name),
                        modifier = Modifier
                            .size(28.dp)
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            )
                            .clip(CircleShape)
                            .padding(4.dp)
                    )
                },
                    colors = SwitchDefaults.colors(
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent,
                    )
                )
            }
        }
    }
}