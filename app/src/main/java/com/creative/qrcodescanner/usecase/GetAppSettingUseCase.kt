package com.creative.qrcodescanner.usecase

import android.content.Context
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.repo.user.UserDataRepo
import com.creative.qrcodescanner.ui.setting.ListSettingUIState
import com.creative.qrcodescanner.ui.setting.SettingItemUIState
import com.creative.qrcodescanner.usecase.base.BaseUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by dan on 20/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */
 
@ViewModelScoped
class GetAppSettingUseCase @Inject constructor(
    private val userDataRepo: UserDataRepo,
    @ApplicationContext private val context: Context
) : BaseUseCase<Unit, ListSettingUIState>() {
    override fun execute(input: Unit): Flow<ListSettingUIState> {
        return userDataRepo.userSettingData.map {
            ListSettingUIState(mutableListOf<SettingItemUIState>().apply {
                add(SettingItemUIState.SettingHeaderUIState(0, title = context.getString(R.string.main_setting)))
                add(
                    SettingItemUIState.SwitchUIState(
                        1, title = context.getString(R.string.sound),
                        iconRes = R.drawable.icon_email, isEnable = false
                    )
                )
                add(
                    SettingItemUIState.SwitchUIState(
                        2, title = context.getString(R.string.vibrate),
                        iconRes = R.drawable.icon_email, isEnable = false
                    )
                )

                add(
                    SettingItemUIState.SettingHeaderUIState(
                        3, title = context.getString(R.string.panda_scanner_setting),
                    )
                )
                add(SettingItemUIState.TextUIState(4, title = context.getString(R.string.about_us), iconRes = R.drawable.icon_calendar_event))
                add(SettingItemUIState.TextUIState(5, title = context.getString(R.string.rate_us), iconRes = R.drawable.icon_calendar_event))
                add(SettingItemUIState.TextUIState(6, title = context.getString(R.string.manage_subscription), iconRes = R.drawable.icon_calendar_event))
            })
        }
    }
}