package com.creative.qrcodescanner.repo.pref

import androidx.datastore.core.DataStore
import com.creative.qrcodescanner.data.entity.UserSettingData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by dan on 20/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@Singleton
class AppPreferenceData @Inject constructor(
    private val userPreferences: DataStore<UserSettingData>
) {
}