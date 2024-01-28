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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView

const val cornerStrokeWidth = 6f
val cornerColor = Color.Blue
val centerColor = Color.Red
const val cornerStrokeLength = 62f

@Composable
fun QRCameraView(lifecycleCameraController: LifecycleCameraController) {

    var centerLineAlpha by remember { androidx.compose.runtime.mutableStateOf(1f) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            centerLineAlpha = if (centerLineAlpha == 1f) 0f else 1f
            kotlinx.coroutines.delay(500)
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
            val color = Color.Black.copy(alpha = 0.5f)
            val shiftTop = w / 8
            drawRect(color = color, size = Size(w, (h - squareSize) / 2 - shiftTop), topLeft = Offset(x = 0f, y = 0f))
            drawRect(color = color, size = Size(w, (h - squareSize) / 2 + shiftTop), topLeft = Offset(x = 0f, y = h - (h - squareSize) / 2 - shiftTop))

            drawRect(color = color, size = Size((w - squareSize) / 2, squareSize), topLeft = Offset(x = 0f, y = (h - squareSize) / 2 - shiftTop))
            drawRect(color = color, size = Size((w - squareSize) / 2, squareSize), topLeft = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop))

            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2 + cornerStrokeLength, y = (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth
            )
            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop + cornerStrokeLength),
                strokeWidth = cornerStrokeWidth
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2 - cornerStrokeLength, y = (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2, y = (h - squareSize) / 2 - shiftTop + cornerStrokeLength),
                strokeWidth = cornerStrokeWidth
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2 + cornerStrokeLength, y = h - (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop - cornerStrokeLength),
                strokeWidth = cornerStrokeWidth
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2 - cornerStrokeLength, y = h - (h - squareSize) / 2 - shiftTop),
                strokeWidth = cornerStrokeWidth
            )

            drawLine(
                color = cornerColor,
                start = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2, y = h - (h - squareSize) / 2 - shiftTop - cornerStrokeLength),
                strokeWidth = 4f
            )

            // draw center line of the square, in horizontal
            drawLine(
                color = centerColor.copy(alpha = centerLineAlpha),
                start = Offset(x = (w - squareSize) / 2, y = h / 2 - shiftTop),
                end = Offset(x = w - (w - squareSize) / 2 , y = h / 2- shiftTop),
                strokeWidth = 4f
            )
        })
    }
}