package com.creative.qrcodescanner.ui.setting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
fun SettingScreenLayout(viewModel: SettingViewModel = hiltViewModel(), appNav: NavHostController) {
    BackHandler {
        appNav.popBackStack()
    }

    Scaffold(topBar = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary, shape = RectangleShape)
        ) {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_back_ios_new),
                    contentDescription = "Setting Back Button",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            appNav.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = stringResource(R.string.panda_scanner_setting),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    },
        bottomBar = {}) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.main_setting), modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 0.dp)
                        .padding(top = 20.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.sound), modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterStart),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = false, onCheckedChange = {}, modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterEnd)
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.vibrate), modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterStart),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = false, onCheckedChange = {},
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterEnd),
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Text(
                    text = stringResource(R.string.about_panda_scanner), modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 0.dp)
                        .padding(top = 20.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.manage_subscription), modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterStart),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icon_calendar_event),
                        contentDescription = "Setting Back Button",
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterEnd)
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.rate_us), modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterStart),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icon_sms),
                        contentDescription = "Setting Back Button",
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterEnd)
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.about_us), modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterStart),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icon_link),
                        contentDescription = "Setting Back Button",
                        modifier = Modifier
                            .size(28.dp)
                            .align(Alignment.CenterEnd)
                    )
                }
                Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.app_version), modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterStart),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "1.0.0", modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(Alignment.CenterEnd),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}