package com.creative.qrcodescanner.ui.main

import android.view.ViewGroup
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.creative.qrcodescanner.ui.theme.seed
import kotlinx.coroutines.delay

val cornerColor = seed
val centerColor = Color.Red
const val cornerStrokeLength = 120f
const val cornerStrokeWidth = 20f
val dimmingColor = Color.Black.copy(alpha = 0.3f)

@Composable
fun QRCameraView(lifecycleCameraController: LifecycleCameraController) {

    var centerLineAlpha by remember { androidx.compose.runtime.mutableStateOf(1f) }

    LaunchedEffect(key1 = Unit) {
        var isIncreasing = false
        while (true) {
            if (centerLineAlpha <= 0f) {
                isIncreasing = true
                centerLineAlpha = 0f
            } else if (centerLineAlpha >= 1f) {
                isIncreasing = false
                centerLineAlpha = 1f
            }
            if (isIncreasing) {
                centerLineAlpha += 0.1f
            } else {
                centerLineAlpha -= 0.1f
            }
            delay(50)
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
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2 , y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2 + cornerStrokeLength, y = (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )
            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop + cornerStrokeLength),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2 - cornerStrokeLength, y = (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop + cornerStrokeLength),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2 + cornerStrokeLength, y = h - (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop - cornerStrokeLength),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2 - cornerStrokeLength, y = h - (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop - cornerStrokeLength),
                strokeWidth = cornerStrokeWidth,
                cap = StrokeCap.Round
            )

            // draw center line of the square, in horizontal
            drawLine(
                color = centerColor,
                start = Offset(x = (w - squareSize) / 2 + 10f, y = h / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2 - 10f, y = h / 2 - shiftTop),
                strokeWidth = 10f,
                alpha = centerLineAlpha.coerceAtLeast(0f).coerceAtMost(1f),
                cap = StrokeCap.Round
            )
        })
    }
}