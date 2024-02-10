package com.creative.qrcodescanner.data.entity

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

data class UserSettingData(
    val isEnableVibrate: Boolean,
    val isEnableSound: Boolean,
    val isPremium: Boolean = false,
    val isKeepScanning: Boolean = false,
) {
    companion object {
        internal val PREFERENCES_NAME = UserSettingData::class.java.name
    }
}

val Context.userSettingPreferences by preferencesDataStore(name = UserSettingData.PREFERENCES_NAME)

object SettingPreferencesKey {
    val IS_ENABLE_VIBRATE = booleanPreferencesKey("is_enable_vibrate")
    val IS_ENABLE_SOUND = booleanPreferencesKey("is_enable_sound")
    val IS_PREMIUM = booleanPreferencesKey("is_premium")
    val IS_KEEP_SCANNING = booleanPreferencesKey("is_keep_scanning")
}