package com.creative.qrcodescanner.ui.premium

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.shadow
import com.creative.qrcodescanner.ui.theme.QRCodeScannerTheme

/**
 * Created by dan on 07/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@Composable
fun PremiumScreenLayout(appNav: NavHostController) {

    val vm: PremiumViewModel = hiltViewModel()
    val premiumUiState by vm.premiumUiStateFlow.collectAsStateWithLifecycle()
    val currentContext = LocalContext.current

    var billingStatusOk by remember {
        mutableStateOf(false)
    }

    val purchasesUpdatedListener =
        remember {
            PurchasesUpdatedListener { billingResult, purchases ->
                // To be implemented in a later section.
            }
        }


    val billingClient = remember {
        BillingClient.newBuilder(currentContext)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
    }

    LaunchedEffect(key1 = Unit) {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    billingStatusOk = true
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    LaunchedEffect(key1 = Unit, block = {
        vm.actionSharedFlow.collect {
            when (it) {
                is PremiumAction.Purchase -> {

                }

                is PremiumAction.MessageToast -> {
                    Toast.makeText(currentContext, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    })

    BackHandler {
        appNav.popBackStack()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            }
        },
        bottomBar = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(18.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterStart)
                        .clickable {
                            appNav.popBackStack()
                        },
                    painter = painterResource(id = R.drawable.icon_close), contentDescription = null
                )
            }
            Box(
                modifier = Modifier.shadow(
                    Color.Cyan.copy(alpha = 0.05f),
                    borderRadius = 128.dp, blurRadius = 128.dp
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_scan),
                    contentDescription = null,
                    modifier = Modifier
                        .size(225.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            val loadedPremiumInfo = (premiumUiState as? PremiumUiState.Loaded)

            Text(
                text = loadedPremiumInfo?.screenTitle ?: "NaN", modifier = Modifier,
                style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                for (benefit in loadedPremiumInfo?.listBenefit ?: emptyList()) {
                    BenefitRow(iconRes = benefit.iconRes, textStr = benefit.title)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 0.dp, vertical = 0.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                for (premiumOption in loadedPremiumInfo?.listPremiumOptions ?: emptyList()) {
                    CtaSubscriptionButton(
                        title = premiumOption.title,
                        subTitle = premiumOption.subTitle
                    ) {
                        vm.onPurchaseClick(premiumOption)
                    }
                }
            }

            Text(
                text = loadedPremiumInfo?.cancelText ?: "NaN", style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light, modifier = Modifier.padding(2.dp)
            )

            Text(
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                text = loadedPremiumInfo?.infoText ?: "NaN",
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview
@Composable
fun PremiumScreenLayoutPreview() {
    QRCodeScannerTheme {
        PremiumScreenLayout(rememberNavController())
    }
}