package com.creative.qrcodescanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.creative.qrcodescanner.ui.QRApp
import com.creative.qrcodescanner.ui.theme.QRCodeScannerTheme

class LauncherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            QRCodeScannerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    QRApp()
                }
            }
        }
    }
}

