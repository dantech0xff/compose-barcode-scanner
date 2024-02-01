package com.creative.qrcodescanner

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by dan on 11/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        setupStrictMode()
    }

    private fun setupStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .penaltyDialog()
            .build())

        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .penaltyDeath()
            .build())
    }
}