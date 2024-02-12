package com.creative.qrcodescanner.repo.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.creative.qrcodescanner.data.entity.SettingPreferencesKey
import com.creative.qrcodescanner.data.entity.UserSettingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Created by dan on 20/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class UserDataRepoImpl(
    private val userSettingDataStore: DataStore<Preferences>
) : UserDataRepo {
    override val userSettingData: Flow<UserSettingData> = userSettingDataStore.data.map {
        val isEnableVibrate = it[SettingPreferencesKey.IS_ENABLE_VIBRATE] ?: false
        val isEnableSound = it[SettingPreferencesKey.IS_ENABLE_SOUND] ?: false
        val isPremium = it[SettingPreferencesKey.IS_PREMIUM] ?: false
        val isKeepScanning = it[SettingPreferencesKey.IS_KEEP_SCANNING] ?: false
        UserSettingData(isEnableVibrate = isEnableVibrate, isEnableSound = isEnableSound,
            isPremium = isPremium, isKeepScanning = isKeepScanning && isPremium)
    }

    override suspend fun updateSoundSetting(isEnableSound: Boolean) {
        userSettingDataStore.edit {
            it[SettingPreferencesKey.IS_ENABLE_SOUND] = isEnableSound
        }
    }

    override suspend fun isEnableSound(): Boolean {
        return userSettingDataStore.data.map { it[SettingPreferencesKey.IS_ENABLE_SOUND] ?: false }.first()
    }

    override suspend fun updateVibrateSetting(isEnableVibrate: Boolean) {
        userSettingDataStore.edit {
            it[SettingPreferencesKey.IS_ENABLE_VIBRATE] = isEnableVibrate
        }
    }

    override suspend fun isEnableVibrate(): Boolean {
        return userSettingDataStore.data.map { it[SettingPreferencesKey.IS_ENABLE_VIBRATE] ?: false }.first()
    }

    override suspend fun updatePremiumSetting(isPremium: Boolean) {
        userSettingDataStore.edit {
            it[SettingPreferencesKey.IS_PREMIUM] = isPremium
        }
    }

    override suspend fun isPremium(): Boolean {
        return userSettingDataStore.data.map { it[SettingPreferencesKey.IS_PREMIUM] ?: false }.first()
    }

    override suspend fun updateKeepScanningSetting(isKeepScanning: Boolean) {
        userSettingDataStore.edit {
            it[SettingPreferencesKey.IS_KEEP_SCANNING] = isKeepScanning
        }
    }

    override suspend fun isKeepScanning(): Boolean {
        return userSettingDataStore.data.map { it[SettingPreferencesKey.IS_KEEP_SCANNING] ?: false }.first() && isPremium()
    }
}