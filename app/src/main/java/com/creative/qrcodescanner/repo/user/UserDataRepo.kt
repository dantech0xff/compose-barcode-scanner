package com.creative.qrcodescanner.repo.user

import com.creative.qrcodescanner.data.entity.UserSettingData
import kotlinx.coroutines.flow.Flow

/**
 * Created by dan on 20/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

interface UserDataRepo {
    val userSettingData: Flow<UserSettingData>

    suspend fun updateSoundSetting(isEnableSound: Boolean)

    suspend fun updateVibrateSetting(isEnableVibrate: Boolean)
}