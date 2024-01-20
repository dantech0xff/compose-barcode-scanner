package com.creative.qrcodescanner.repo.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.creative.qrcodescanner.data.entity.SettingPreferencesKey
import com.creative.qrcodescanner.data.entity.UserSettingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
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
            UserSettingData(isEnableVibrate = isEnableVibrate, isEnableSound = isEnableSound)
        }

    override suspend fun updateSoundSetting(isEnableSound: Boolean) {
        userSettingDataStore.edit {
            it[SettingPreferencesKey.IS_ENABLE_SOUND] = isEnableSound
        }
    }

    override suspend fun isEnableSound(): Boolean {
        return userSettingDataStore.data.map { it[SettingPreferencesKey.IS_ENABLE_SOUND] ?: false }.last()
    }

    override suspend fun updateVibrateSetting(isEnableVibrate: Boolean) {
        userSettingDataStore.edit {
            it[SettingPreferencesKey.IS_ENABLE_VIBRATE] = isEnableVibrate
        }
    }

    override suspend fun isEnableVibrate(): Boolean {
        return userSettingDataStore.data.map { it[SettingPreferencesKey.IS_ENABLE_VIBRATE] ?: false }.last()
    }
}