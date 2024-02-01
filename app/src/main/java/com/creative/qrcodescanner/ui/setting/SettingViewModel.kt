package com.creative.qrcodescanner.ui.setting

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.qrcodescanner.usecase.GetAppSettingFlowUseCase
import com.creative.qrcodescanner.usecase.UpdateSoundSettingUseCase
import com.creative.qrcodescanner.usecase.UpdateVibrateSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by dan on 19/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@HiltViewModel
class SettingViewModel @Inject constructor(
    getAppSettingUseCase: GetAppSettingFlowUseCase,
    private val updateSoundSettingUseCase: UpdateSoundSettingUseCase,
    private val updateVibrateSettingUseCase: UpdateVibrateSettingUseCase
) : ViewModel() {

    val listSettingUIState: StateFlow<ListSettingUIState> =
        getAppSettingUseCase.execute(Unit).stateIn(viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListSettingUIState(emptyList()))

    private val _toastSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val toastSharedFlow: SharedFlow<String> = _toastSharedFlow

    fun handleSetting(settingItem: SettingItemUIState) {
        when (settingItem) {
            is SettingItemUIState.SwitchUIState -> {
                when (settingItem.id) {
                    SettingId.SOUND.value -> {
                        viewModelScope.launch {
                            updateSoundSettingUseCase.execute(!settingItem.isEnable)
                        }
                    }

                    SettingId.VIBRATE.value -> {
                        viewModelScope.launch {
                            updateVibrateSettingUseCase.execute(!settingItem.isEnable)
                        }
                    }
                }
            }

            is SettingItemUIState.TextUIState -> {
                when (settingItem.id) {
                    SettingId.ABOUT_US.value -> {
                        _toastSharedFlow.tryEmit(settingItem.title)
                    }

                    SettingId.RATE_US.value -> {
                        _toastSharedFlow.tryEmit(settingItem.title)
                    }

                    SettingId.MANAGE_SUBSCRIPTION.value -> {
                        _toastSharedFlow.tryEmit(settingItem.title)
                    }
                }
            }

            else -> {}
        }
    }
}

data class ListSettingUIState(val data: List<SettingItemUIState>)

sealed class SettingItemUIState(open val id: Int) {

    data class SettingHeaderUIState(
        override val id: Int,
        val title: String,
        val description: String = "",
    ) : SettingItemUIState(id)

    data class TextUIState(
        override val id: Int,
        val title: String,
        val description: String = "",
        @DrawableRes val iconRes: Int,
    ) : SettingItemUIState(id)

    data class SwitchUIState(
        override val id: Int,
        val title: String,
        val isEnable: Boolean = false,
    ) : SettingItemUIState(id)

    data class DividerUIState(
        override val id: Int,
    ) : SettingItemUIState(id)
}

enum class SettingId(val value: Int) {
    NONE(0),
    SOUND(1),
    VIBRATE(2),
    ABOUT_US(3),
    RATE_US(4),
    MANAGE_SUBSCRIPTION(5),
}