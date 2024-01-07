package com.creative.qrcodescanner.ui.layout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.creative.qrcodescanner.LauncherViewModel
import com.creative.qrcodescanner.ui.main.MainScreenLayout
import com.creative.qrcodescanner.ui.result.QRCodeResultLayout

@Composable
fun QRApp(vm: LauncherViewModel = viewModel(),
          appNavHost: NavHostController = rememberNavController()) {
    val qrCodeResult by vm.qrCodeResultState.collectAsStateWithLifecycle()
    NavHost(
        navController = appNavHost,
        modifier = Modifier.fillMaxSize(),
        startDestination = AppScreen.MAIN.value
    ) {
        composable(route = AppScreen.MAIN.value) {
            MainScreenLayout(vm, appNavHost)
        }
        composable(route = AppScreen.RESULT.value) {
            QRCodeResultLayout(qrCodeResult, appNavHost, {
                vm.resetScanQR()
            }, {})
        }
    }
}

enum class AppScreen(val value: String) {
    MAIN("main"),
    SETTING("setting"),
    PREMIUM("premium"),
    GALLERY("gallery"),
    HISTORY("history"),
    RESULT("result")
}