package com.creative.qrcodescanner.ui.history

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.nav.TopNavBar
import com.creative.qrcodescanner.ui.shadow
import com.creative.qrcodescanner.usecase.QRCodeHistoryUIState

/**
 * Created by dan on 07/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryScreenLayout(viewModel: HistoryViewModel = hiltViewModel(),
                        appNav: NavHostController) {

    val qrCodeHistoryUIState by viewModel.qrCodeHistoryUIState.collectAsStateWithLifecycle()

    BackHandler {
        appNav.popBackStack()
    }

    Scaffold(topBar = {
        TopNavBar(titleResId = R.string.qr_code_history) {
            appNav.popBackStack()
        }
    },
        bottomBar = {}
    ) { paddingValues ->
        AnimatedVisibility(visible = qrCodeHistoryUIState is QRCodeHistoryUIState.Empty, enter = fadeIn(), exit = fadeOut()) {
            if (qrCodeHistoryUIState !is QRCodeHistoryUIState.Empty) return@AnimatedVisibility
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .shadow(color = Color.Black.copy(alpha = 0.15f), blurRadius = 8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            appNav.popBackStack()
                        }
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = R.drawable.empty_state), contentDescription = stringResource(id = R.string.qr_code_history))
                    Text(
                        text = stringResource(R.string.no_qr_code_history),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                    )
                }
            }
        }

        AnimatedVisibility(visible = qrCodeHistoryUIState is QRCodeHistoryUIState.Loading, enter = fadeIn(), exit = fadeOut()) {
            if (qrCodeHistoryUIState !is QRCodeHistoryUIState.Loading) return@AnimatedVisibility
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .shadow(color = Color.Black.copy(alpha = 0.15f), blurRadius = 8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            appNav.popBackStack()
                        }
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = R.drawable.loading_state), contentDescription = stringResource(id = R.string.qr_code_history))
                    LinearProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .height(4.dp)
                            .fillMaxWidth().background(
                                color = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(4.dp)
                            ).clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.inversePrimary
                    )
                    Text(
                        text = stringResource(R.string.no_qr_code_history),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                    )
                }
            }
        }

        AnimatedVisibility(visible = qrCodeHistoryUIState is QRCodeHistoryUIState.Success, enter = fadeIn(), exit = fadeOut()) {
            if (qrCodeHistoryUIState !is QRCodeHistoryUIState.Success) return@AnimatedVisibility

            val data = (qrCodeHistoryUIState as QRCodeHistoryUIState.Success).data
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier,
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item { Spacer(modifier = Modifier) }
                    items(data, key = {
                        it.id
                    }, contentType = {
                        it.type
                    }) { item ->
                        QRCodeHistoryItemUI(
                            Modifier
                                .animateItemPlacement(
                                    animationSpec = tween(500, easing = EaseInOutBack)
                                )
                                .animateContentSize(), itemUiState = item, onDelete = {
                                viewModel.deleteQRCodeHistory(it.id)
                            }, onClick = {
                                appNav.navigate("result/${item.id}")
                            })
                    }
                    item { Spacer(modifier = Modifier) }
                }
            }
        }

        AnimatedVisibility(visible = qrCodeHistoryUIState is QRCodeHistoryUIState.Error, enter = fadeIn(), exit = fadeOut()) {
            if (qrCodeHistoryUIState !is QRCodeHistoryUIState.Error) return@AnimatedVisibility

            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .shadow(color = Color.Black.copy(alpha = 0.15f), blurRadius = 8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            appNav.popBackStack()
                        }
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(id = R.drawable.error_state), contentDescription = stringResource(id = R.string.qr_code_history))
                    Text(
                        text = (qrCodeHistoryUIState as QRCodeHistoryUIState.Error).throwable.message.orEmpty(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}