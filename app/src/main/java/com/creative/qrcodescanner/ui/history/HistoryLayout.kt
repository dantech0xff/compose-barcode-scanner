package com.creative.qrcodescanner.ui.history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.theme.fontSize
import com.creative.qrcodescanner.usecase.QRCodeHistoryUIState

/**
 * Created by dan on 07/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@Composable
fun HistoryScreenLayout(viewModel: HistoryViewModel = hiltViewModel(),
                        appNav: NavHostController) {

    val qrCodeHistoryUIState by viewModel.qrCodeHistoryUIState.collectAsStateWithLifecycle()

    BackHandler {
        appNav.popBackStack()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary, shape = RectangleShape)
            ) {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

                Text(
                    text = stringResource(R.string.qr_code_history),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(12.dp),
                    color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp, vertical = 0.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        appNav.popBackStack()
                    }
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_qr_white),
                        contentDescription = stringResource(id = R.string.continue_to_scan),
                        modifier = Modifier.size(28.dp)
                    )
                    Text(text = stringResource(R.string.continue_to_scan), color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            if (qrCodeHistoryUIState is QRCodeHistoryUIState.Success) {
                Text(text = (qrCodeHistoryUIState as QRCodeHistoryUIState.Success).data.size.toString(), textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center))
            } else {
                Text(text = "No data", textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}