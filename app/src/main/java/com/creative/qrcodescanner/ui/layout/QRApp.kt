package com.creative.qrcodescanner.ui.layout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.creative.qrcodescanner.LauncherViewModel
import com.creative.qrcodescanner.ui.main.MainScreenLayout

@Composable
fun QRApp(vm: LauncherViewModel = viewModel(),
          appNavHost: NavHostController = rememberNavController()) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {}, bottomBar = {}) { innerPadding ->
        NavHost(
            navController = appNavHost,
            modifier = Modifier.padding(innerPadding),
            startDestination = AppScreen.MAIN.value
        ) {
            composable(route = AppScreen.MAIN.value) {
                MainScreenLayout(vm, appNavHost)
            }
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