package com.creative.qrcodescanner.ui.history

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemGestures
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.nav.TopNavBar
import com.creative.qrcodescanner.ui.theme.fontSize
import com.creative.qrcodescanner.usecase.QRCodeHistoryUIState
import java.util.Date

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

    Scaffold(
        topBar = {
            TopNavBar(titleResId = R.string.qr_code_history) {
                appNav.popBackStack()
            }
        },
        bottomBar = {}
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(paddingValues)
        ) {
            when (qrCodeHistoryUIState) {
                is QRCodeHistoryUIState.Empty -> {
                    Text(text = stringResource(R.string.no_qr_code_history),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                appNav.popBackStack()
                            }
                    )
                }

                is QRCodeHistoryUIState.Error -> {
                    Text(text = (qrCodeHistoryUIState as QRCodeHistoryUIState.Error).throwable.message.orEmpty(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clickable {
                                appNav.popBackStack()
                            }
                    )
                }

                is QRCodeHistoryUIState.Success -> {
                    val data = (qrCodeHistoryUIState as QRCodeHistoryUIState.Success).data
                    LazyColumn(modifier = Modifier.fillMaxHeight(), state = rememberLazyListState()) {
                        items(data, key = {
                            it.id
                        }, contentType = {
                            it.type
                        }) { item ->
                            QRCodeHistoryItemUI(Modifier.animateItemPlacement(
                                animationSpec = tween(500, easing = EaseInOutBack)
                            ),
                                itemUiState = item, onDelete = {
                                    viewModel.deleteQRCodeHistory(it.id)
                                }, onClick = {
                                    appNav.navigate("result/${item.id}")
                                })
                        }
                    }
                }

                else -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}