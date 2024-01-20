package com.creative.qrcodescanner.repo.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.creative.qrcodescanner.data.entity.SettingPreferencesKey
import com.creative.qrcodescanner.data.entity.UserSettingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by dan on 20/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class UserDataRepoImpl(
    private val userSettingDataStore: DataStore<Preferences>
) : UserDataRepo {
    override val userSettingData: Flow<UserSettingData>
        get() = userSettingDataStore.data.map {
            val isEnableVibrate = it[SettingPreferencesKey.IS_ENABLE_VIBRATE] ?: false
            val isEnableSound = it[SettingPreferencesKey.IS_ENABLE_SOUND] ?: false
            UserSettingData(isEnableVibrate = isEnableVibrate, isEnableSound = isEnableSound)
        }
}