package com.creative.qrcodescanner.ui.setting

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.AppScreen
import com.creative.qrcodescanner.ui.nav.TopNavBar
import com.creative.qrcodescanner.ui.shadow
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by dan on 07/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingScreenLayout(viewModel: SettingViewModel = hiltViewModel(), appNav: NavHostController) {
    val listSetting by viewModel.listSettingUIState.collectAsStateWithLifecycle()

    val localContext = LocalContext.current

    BackHandler {
        appNav.popBackStack()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.toastSharedFlow.collectLatest {
            Toast.makeText(localContext, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = viewModel.navigationSharedFlow) {
        viewModel.navigationSharedFlow.collectLatest {
            when (it) {
                SettingNavigation.ABOUT_US -> {

                }

                SettingNavigation.RATE_US -> {

                }

                SettingNavigation.MANAGE_SUBSCRIPTION -> {
                    appNav.navigate(AppScreen.PREMIUM.value)
                }
            }
        }
    }

    Scaffold(topBar = {
        TopNavBar(titleResId = R.string.panda_scanner_setting) {
            appNav.popBackStack()
        }
    }, bottomBar = {}
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .background(color = Color.White)
                .wrapContentSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                    .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                    .wrapContentSize()
            ) {

                LazyColumn(modifier = Modifier.wrapContentSize(), state = rememberLazyListState()) {
                    items(listSetting.data, key = {
                        it.hashCode()
                    }) {
                        var settingItemState by remember {
                            mutableStateOf(it)
                        }
                        settingItemState = it
                        val settingItemModifier = remember {
                            Modifier.animateItemPlacement(animationSpec = tween(500, easing = EaseInOutBack))
                        }
                        val onSettingItemClick = remember {
                            {
                                viewModel.handleSetting(settingItemState)
                            }
                        }
                        when (it) {
                            is SettingItemUIState.SettingHeaderUIState -> {
                                SettingHeaderItem(
                                    modifier = settingItemModifier, settingItem = it
                                )
                            }

                            is SettingItemUIState.TextUIState -> {
                                SettingTextItem(
                                    modifier = settingItemModifier,
                                    settingItem = it,
                                    onClickSetting = onSettingItemClick
                                )
                            }

                            is SettingItemUIState.SwitchUIState -> {
                                SettingSwitchItem(
                                    modifier = settingItemModifier, settingItem = it, onClickSetting = onSettingItemClick
                                )
                            }

                            is SettingItemUIState.DividerUIState -> {
                                Divider(
                                    modifier = settingItemModifier
                                        .padding(vertical = 2.dp)
                                        .fillMaxWidth()
                                        .heightIn(min = 0.5.dp)
                                        .padding(horizontal = 16.dp),
                                    thickness = 0.5.dp,
                                    color = Color.LightGray.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                    item {
                        val settingItem = remember {
                            Modifier.animateItemPlacement(animationSpec = tween(500, easing = EaseInOutBack))
                        }
                        Spacer(
                            modifier = settingItem.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}