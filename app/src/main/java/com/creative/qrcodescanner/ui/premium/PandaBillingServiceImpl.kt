package com.creative.qrcodescanner.ui.premium

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.QueryProductDetailsParams

/**
 * Created by dan on 13/02/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class PandaBillingServiceImpl(private val activity: Activity, private val onPurchaseSuccess: (Boolean) -> Unit = {}) : PandaBillingService {

    private var billingAvailableStatus: BillingAvailableStatus = BillingAvailableStatus.UNAVAILABLE

    private val billingClient =
        BillingClient.newBuilder(activity)
            .setListener { billingResult, purchases ->
                // To be implemented in a later section.
                Log.d("PandaBillingServiceImpl", "billingResult: $billingResult, purchases: $purchases")
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    onPurchaseSuccess(true)
                }
            }
            .enablePendingPurchases()
            .build()

    override fun startBillingService() {
        if (billingAvailableStatus == BillingAvailableStatus.AVAILABLE || billingAvailableStatus == BillingAvailableStatus.PROCESSING)
            return

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                billingAvailableStatus = if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    BillingAvailableStatus.AVAILABLE
                } else {
                    BillingAvailableStatus.UNAVAILABLE
                }
                Log.d("PandaBillingServiceImpl", "billingResult: $billingResult")
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                billingAvailableStatus = BillingAvailableStatus.UNAVAILABLE
                Log.d("PandaBillingServiceImpl", "onBillingServiceDisconnected")
            }
        })
    }

    override fun purchaseProduct(productId: String, purchaseType: PremiumPurchaseType) {

        startBillingService()

        if (billingAvailableStatus != BillingAvailableStatus.AVAILABLE)
            return

        Log.d("PandaBillingServiceImpl", "purchaseProduct: $productId, $purchaseType")

        billingClient.queryProductDetailsAsync(
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    listOf(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(productId)
                            .setProductType(
                                if (purchaseType == PremiumPurchaseType.SUBSCRIPTION) {
                                    BillingClient.ProductType.SUBS
                                } else {
                                    BillingClient.ProductType.INAPP
                                }
                            )
                            .build()
                    )
                )
                .build()
        ) { billingResult,
            productDetailsList ->
            Log.d("PandaBillingServiceImpl", "billingResult: $billingResult")

            if (billingResult.responseCode != BillingClient.BillingResponseCode.OK)
                return@queryProductDetailsAsync

            Log.d("PandaBillingServiceImpl", "productDetailsList: $productDetailsList")
            val productDetails = productDetailsList.getOrNull(0) ?: return@queryProductDetailsAsync

            val offerToken = productDetails.subscriptionOfferDetails?.let { listOffers ->
                listOffers.getOrNull(0)?.offerToken ?: ""
            } ?: ""

            val billingFlowResult = billingClient.launchBillingFlow(
                activity, BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(
                        listOf(
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .let { builder ->
                                    if (offerToken.isNotEmpty())
                                        builder.setOfferToken(offerToken)
                                    else
                                        builder
                                }.build()
                        )
                    )
                    .build()
            )
            Log.d("PandaBillingServiceImpl", "billingFlowResult: $billingFlowResult")
        }
    }
}