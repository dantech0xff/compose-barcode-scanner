package com.creative.qrcodescanner.usecase.premium

import android.content.Context
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.premium.PremiumBenefitItem
import com.creative.qrcodescanner.ui.premium.PremiumPurchaseItem
import com.creative.qrcodescanner.ui.premium.PremiumPurchaseType
import com.creative.qrcodescanner.ui.premium.PremiumUiState
import com.creative.qrcodescanner.ui.premium.PremiumViewModel
import com.creative.qrcodescanner.usecase.base.BaseFlowUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by dan on 12/02/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@ViewModelScoped
class GetPremiumUiStateFlowUseCase @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : BaseFlowUseCase<Unit, PremiumUiState>() {
    override fun execute(input: Unit): Flow<PremiumUiState> {
        return flow {
            emit(
                PremiumUiState.Loaded(
                    applicationContext.getString(R.string.app_name),
                    listOf(
                        PremiumBenefitItem(applicationContext.getString(R.string.no_advertising), R.drawable.icon_verified),
                        PremiumBenefitItem(applicationContext.getString(R.string.prevent_duplicate_in_history), R.drawable.icon_document),
                        PremiumBenefitItem(applicationContext.getString(R.string.continuous_scanning), R.drawable.icon_scan),
//                        PremiumBenefitItem(applicationContext.getString(R.string.text_scan_translate), R.drawable.icon_message),
                    ),
                    listOf(
                        PremiumPurchaseItem(
                            applicationContext.getString(R.string.one_time_purchase), applicationContext.getString(R.string.only_9_99_forever_premium),
                                    "com.creative.qrcodescanner.onetime", PremiumPurchaseType.ONE_TIME_PURCHASE
                        ),
                        PremiumPurchaseItem(
                            applicationContext.getString(R.string.three_day_free_trial),
                            applicationContext.getString(R.string.only_2_99_first_3_months),
                            "com.creative.qrcodescanner.subscription", PremiumPurchaseType.SUBSCRIPTION
                        ),
                    ),
                    applicationContext.getString(R.string.cancel_anytime),
                    applicationContext.getString(R.string.premium_notice_msg)
                )
            )
        }
    }
}