package com.creative.qrcodescanner.ui.premium

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.shadow
import com.creative.qrcodescanner.ui.theme.QRCodeScannerTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

/**
 * Created by dan on 07/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@Composable
fun PremiumScreenLayout(viewModel: PremiumViewModel = hiltViewModel(), appNav: NavHostController) {

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
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(12.dp)
                        .shadow(Color.Black.copy(alpha = 0.05f))
                        .clip(CircleShape)
                        .clickable {
                            appNav.popBackStack()
                        }
                ) {
                    Image(
                        modifier = Modifier.size(42.dp),
                        painter = painterResource(id = R.drawable.icon_close), contentDescription = null
                    )
                }
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

            Box(modifier = Modifier.shadow(Color.Cyan.copy(alpha = 0.05f),
                borderRadius = 128.dp, blurRadius = 128.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.icon_scan),
                    contentDescription = null,
                    modifier = Modifier
                        .size(225.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Text(
                text = "Panda Scanner", modifier = Modifier,
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
                BenefitRow(R.drawable.icon_verified, R.string.no_advertising)
                BenefitRow(iconRes = R.drawable.icon_document, textRes = R.string.prevent_duplicate_in_history)
                BenefitRow(iconRes = R.drawable.icon_scan, textRes = R.string.continuous_scanning)
                BenefitRow(iconRes = R.drawable.icon_message, textRes = R.string.text_scan_translate)
            }

            CtaSubscriptionButton("7-days free trial", "Only 20.99$ per year after trial")
            Spacer(modifier = Modifier.size(6.dp))
//            CtaSubscriptionButton("3-days free trial", "Only 2.99$ per month after trial")
            CtaSubscriptionButton("Paid Weekly", "Only 0.99$ per week")

            Text(text = "(CANCEL ANYTIME)", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Light, modifier = Modifier.padding(2.dp))

            Text(
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                text = "You will have access to all features after purchasing the VIP Subscription. Your subscription will" +
                        "automatically renew at the same price and period. If your subscription includes a free trial, " +
                        "you'll be charged at the end of your free trial, " +
                        "you may manage or cancel subscriptions at any time in " +
                        "Subscriptions on Google Play."
            )
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview
@Composable
fun PremiumScreenLayoutPreview() {
    QRCodeScannerTheme {
        PremiumScreenLayout(PremiumViewModel(), rememberNavController())
    }
}