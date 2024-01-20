package com.creative.qrcodescanner.ui.setting

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.qrcodescanner.usecase.GetAppSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Created by dan on 19/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@HiltViewModel
class SettingViewModel @Inject constructor(
    getAppSettingUseCase: GetAppSettingUseCase
) : ViewModel() {

    val listSettingUIState: StateFlow<ListSettingUIState> =
        getAppSettingUseCase.execute(Unit).stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = ListSettingUIState(emptyList()))

    fun handleSetting(settingId: String) {

    }
}

data class ListSettingUIState(
    val data: List<SettingItemUIState>
)

sealed class SettingItemUIState(open val id: Int, open val type: Int) {

    data class SettingHeaderUIState(
        override val id: Int,
        val title: String,
        val description: String = "",
    ) : SettingItemUIState(id, SettingType.HEADER.value)

    data class TextUIState(
        override val id: Int,
        val title: String,
        val description: String = "",
        @DrawableRes val iconRes: Int,
    ) : SettingItemUIState(id, SettingType.TEXT.value)

    data class SwitchUIState(
        override val id: Int,
        val title: String,
        val description: String = "",
        @DrawableRes val iconRes: Int,
        val isEnable: Boolean = false,
    ) : SettingItemUIState(id, SettingType.SWITCH.value)
}

enum class SettingType(val value: Int) {
    HEADER(0),
    TEXT(1),
    SWITCH(2),
}