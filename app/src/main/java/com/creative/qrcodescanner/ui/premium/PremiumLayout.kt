package com.creative.qrcodescanner.ui.premium

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.creative.qrcodescanner.R

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
//            TopNavBar(titleResId = R.string.qr_code_history) {
//                appNav.popBackStack()
//            }
        },
        bottomBar = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(paddingValues)
        ) {
            Image(painter = painterResource(id = R.drawable.icon_qr), contentDescription = null, modifier = Modifier.size(100.dp))

            Row {
                Image(painter = painterResource(id = R.drawable.icon_email), contentDescription = null, modifier = Modifier.size(24.dp))
                Text(text = "No Advertising", modifier = Modifier.padding(start = 8.dp))
            }

            Row {
                Image(painter = painterResource(id = R.drawable.icon_email), contentDescription = null, modifier = Modifier.size(24.dp))
                Text(text = "Remove Duplicate in History", modifier = Modifier.padding(start = 8.dp))
            }

            Row {
                Image(painter = painterResource(id = R.drawable.icon_email), contentDescription = null, modifier = Modifier.size(24.dp))
                Text(text = "Continuous Scanning", modifier = Modifier.padding(start = 8.dp))
            }

            Row {
                Image(painter = painterResource(id = R.drawable.icon_email), contentDescription = null, modifier = Modifier.size(24.dp))
                Text(text = "Text Scan & Translate", modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Box {
                Column {
                    Text(text = "3-day free trial, Paid Yearly", modifier = Modifier.padding(start = 8.dp))
                    Text(text = "Only 15$ per year after trial", modifier = Modifier.padding(start = 8.dp))
                }
            }

            Box {
                Column {
                    Text(text = "3-day free trial, Paid Monthly", modifier = Modifier.padding(start = 8.dp))
                    Text(text = "Only 3$ per month after trial", modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.padding(4.dp))

            Box {
                Column {
                    Text(text = "Paid Weekly", modifier = Modifier.padding(start = 8.dp))
                    Text(text = "Only 1$ per week", modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.padding(4.dp))

            Box {
                Text(text = "CONTINUE", modifier = Modifier.padding(8.dp))
            }

            Text(text = "(CANCEL ANYTIME)", modifier = Modifier.padding(1.dp))

            Text(
                text = "You will have access to all features after purchasing the VIP Subscription. Your subscription will" +
                        "automatically renew at the same price and period. If your subscription includes a free trial, " +
                        "you'll be charged at the end of your free trial, " +
                        "you may manage or cancel subscriptions at any time in " +
                        "Subscriptions on Google Play.", modifier = Modifier.padding(2.dp)
            )
        }
    }
}