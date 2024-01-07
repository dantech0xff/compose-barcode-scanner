package com.creative.qrcodescanner.ui

import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.withResumed
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.creative.qrcodescanner.LauncherViewModel
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.history.HistoryScreenLayout
import com.creative.qrcodescanner.ui.main.MainScreenLayout
import com.creative.qrcodescanner.ui.premium.PremiumScreenLayout
import com.creative.qrcodescanner.ui.result.QRCodeResultLayout
import com.creative.qrcodescanner.ui.setting.SettingScreenLayout

@Composable
fun QRApp(vm: LauncherViewModel = viewModel(),
          appNavHost: NavHostController = rememberNavController()) {
    val qrCodeResult by vm.qrCodeResultState.collectAsStateWithLifecycle(null)

    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(key1 = Unit) {
        Log.d("QRApp", "openUrlState: collect")
        vm.openUrlState.collect { url ->
            Log.d("QRApp", "openUrlState: $url")
            lifecycle.withResumed {
                if (url.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.copyTextState.collect { copyText ->
            Log.d("QRApp", "copyTextState: $copyText")
            if (copyText.isNotEmpty()) {
                val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("Copied Text", copyText)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, context.resources.getString(R.string.copied_to_clipboard, copyText), Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.contactInfoState.collect {
            Log.d("QRApp", "contactInfoState: $it")
            val intent = Intent(Intent.ACTION_INSERT).apply {
                type = ContactsContract.Contacts.CONTENT_TYPE
                putExtra(ContactsContract.Intents.Insert.NAME, it.name?.formattedName)
                putExtra(ContactsContract.Intents.Insert.EMAIL, it.emails.firstOrNull()?.address)
                putExtra(ContactsContract.Intents.Insert.PHONE, it.phones.firstOrNull()?.number)
                putExtra(ContactsContract.Intents.Insert.COMPANY, it.organization)
                putExtra(ContactsContract.Intents.Insert.JOB_TITLE, it.title)
                putExtra(ContactsContract.Intents.Insert.NOTES, it.urls.firstOrNull())
            }
            context.startActivity(intent)
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        vm.textSearchGoogleState.collect {
            Log.d("QRApp", "textSearchGoogleState: $it")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=$it"))
            context.startActivity(intent)
        }
    })

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
            }, { barCode ->
                barCode?.let { vm.handleBarcodeResult(it) }
            })
        }
        composable(route = AppScreen.SETTING.value) {
            SettingScreenLayout()
        }
        composable(route = AppScreen.HISTORY.value) {
            HistoryScreenLayout()
        }
        composable(route = AppScreen.PREMIUM.value) {
            PremiumScreenLayout()
        }
    }
}

enum class AppScreen(val value: String) {
    MAIN("main"),
    SETTING("setting"),
    PREMIUM("premium"),
    HISTORY("history"),
    RESULT("result")
}