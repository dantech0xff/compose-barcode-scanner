package com.creative.qrcodescanner.ui.setting

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.nav.TopNavBar
import com.creative.qrcodescanner.ui.shadow
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by dan on 07/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

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
                    .padding(20.dp)
                    .shadow(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                    .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                    .wrapContentSize()
            ) {
                LazyColumn(modifier = Modifier.wrapContentSize()) {
                    items(listSetting.data) {
                        when (it) {
                            is SettingItemUIState.SettingHeaderUIState -> {
                                SettingHeaderItem(settingItem = it)
                            }

                            is SettingItemUIState.TextUIState -> {
                                SettingTextItem(settingItem = it, onClickSetting = viewModel::handleSetting)
                            }

                            is SettingItemUIState.SwitchUIState -> {
                                SettingSwitchItem(settingItem = it, onClickSetting = viewModel::handleSetting)
                            }

                            is SettingItemUIState.DividerUIState -> {
                                Divider(
                                    modifier =
                                    Modifier
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
                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }
            }
        }
    }
}