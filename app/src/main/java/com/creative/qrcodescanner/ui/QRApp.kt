package com.creative.qrcodescanner.ui

import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
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
fun QRApp(vm: LauncherViewModel = hiltViewModel(),
          appNavHost: NavHostController = rememberNavController()) {

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
                putExtra(ContactsContract.Intents.Insert.NAME, it.name)
                putExtra(ContactsContract.Intents.Insert.EMAIL, it.email?.firstOrNull())
                putExtra(ContactsContract.Intents.Insert.PHONE, it.phone?.firstOrNull())
                putExtra(ContactsContract.Intents.Insert.COMPANY, it.organization)
                putExtra(ContactsContract.Intents.Insert.JOB_TITLE, it.title)
                putExtra(ContactsContract.Intents.Insert.NOTES, it.urls?.firstOrNull())
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

    LaunchedEffect(key1 = Unit) {
        vm.textShareActionState.collect {
            Log.d("QRApp", "textShareActionState: $it")
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, it)
            }
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)))
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.callPhoneActionState.collect {
            Log.d("QRApp", "callPhoneActionState: $it")
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it"))
            context.startActivity(intent)
        }
    }

    LaunchedEffect(key1 = Unit) {
        vm.sendSMSActionState.collect {
            Log.d("QRApp", "sendSMSActionState: $it")
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${it.number}"))
            intent.putExtra("sms_body", it.message)
            context.startActivity(intent)
        }
    }

    NavHost(
        navController = appNavHost,
        modifier = Modifier.fillMaxSize(),
        startDestination = AppScreen.MAIN.value
    ) {
        composable(route = AppScreen.MAIN.value) {
            MainScreenLayout(vm, appNavHost)
        }
        composable(route = AppScreen.RESULT.value) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")?.toInt() ?: 0
            QRCodeResultLayout(id, appNavHost, hiltViewModel(),
                {
                    vm.resetScanQR()
                }, { barCode ->
                    barCode?.let { vm.handleBarcodeResult(it) }
                }, {
                    // handle copy
                    vm.handleCopyText(it)
                }, {
                    // handle share
                    vm.handleShareText(it)
                })
        }
        composable(route = AppScreen.SETTING.value) {
            SettingScreenLayout(appNav = appNavHost)
        }
        composable(route = AppScreen.HISTORY.value) {
            HistoryScreenLayout(appNav = appNavHost)
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
    RESULT("result/{id}")
}