package com.creative.qrcodescanner.ui.premium

/**
 * Created by dan on 13/02/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

interface PandaBillingService {
    fun startBillingService()
    fun purchaseProduct(productId: String, purchaseType: PremiumPurchaseType)
}

enum class BillingAvailableStatus {
    AVAILABLE,
    PROCESSING,
    UNAVAILABLE
}