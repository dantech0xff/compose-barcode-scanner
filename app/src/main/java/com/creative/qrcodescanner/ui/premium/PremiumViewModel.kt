package com.creative.qrcodescanner.ui.premium

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.qrcodescanner.usecase.premium.GetPremiumUiStateFlowUseCase
import com.creative.qrcodescanner.usecase.setting.UpdatePremiumSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by dan on 21/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@Stable
sealed class PremiumAction{
    data class MessageToast(val message: String) : PremiumAction()
}

@HiltViewModel
class PremiumViewModel @Inject constructor(
    getPremiumUiStateFlowUseCase: GetPremiumUiStateFlowUseCase,
    private val updatePremiumSettingUseCase: UpdatePremiumSettingUseCase
) : ViewModel() {

    val premiumUiStateFlow: StateFlow<PremiumUiState> = getPremiumUiStateFlowUseCase.execute(Unit).stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PremiumUiState.Loading
    )

    private val _actionSharedFlow: MutableSharedFlow<PremiumAction> = MutableSharedFlow(extraBufferCapacity = 1)
    val actionSharedFlow: SharedFlow<PremiumAction> = _actionSharedFlow

    fun updatePremiumSetting(isPremium: Boolean) {
        viewModelScope.launch {
            if (isPremium) {
                _actionSharedFlow.emit(PremiumAction.MessageToast("Thank you for purchasing premium"))
                updatePremiumSettingUseCase.execute(true)
            }
        }
    }
}

@Stable
sealed class PremiumUiState {

    data object Loading : PremiumUiState()

    data class Loaded(
        val screenTitle: String,
        val listBenefit: List<PremiumBenefitItem>,
        val listPremiumOptions: List<PremiumPurchaseItem>,
        val cancelText: String,
        val infoText: String
    ) : PremiumUiState()

    data class Error(
        val message: String
    ) : PremiumUiState()
}

@Stable
data class PremiumBenefitItem(
    val title: String,
    @DrawableRes val iconRes: Int
)

@Stable
data class PremiumPurchaseItem(
    val title: String,
    val subTitle: String,
    val productId: String,
    val purchaseType: PremiumPurchaseType
)

@Stable
enum class PremiumPurchaseType(val value: Int) {
    SUBSCRIPTION(0), ONE_TIME_PURCHASE(1)
}