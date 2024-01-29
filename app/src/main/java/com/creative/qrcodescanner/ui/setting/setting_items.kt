package com.creative.qrcodescanner.ui.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creative.qrcodescanner.R

/**
 * Created by dan on 28/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */
 
@Composable
fun SettingSwitchItem(settingItem: SettingItemUIState.SwitchUIState, onClickSetting: (SettingItemUIState) -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                onClickSetting.invoke(settingItem)
            }
            .heightIn(min = 48.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = settingItem.title, modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(Alignment.CenterStart),
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            modifier = Modifier.align(Alignment.CenterEnd),
            checked = settingItem.isEnable, onCheckedChange = null,
            thumbContent = {
                Image(
                    painter = painterResource(id = R.drawable.icon_qr),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier.size(28.dp).background(
                        color = Color.White,
                        shape = CircleShape
                    ).clip(CircleShape).padding(4.dp)
                )
            },
            colors = SwitchDefaults.colors(
                checkedBorderColor = Color.Transparent,
                uncheckedBorderColor = Color.Transparent,
            )
        )
    }
}

@Composable
fun SettingTextItem(settingItem: SettingItemUIState.TextUIState, onClickSetting: (SettingItemUIState) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                onClickSetting.invoke(settingItem)
            }
            .heightIn(min = 48.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = settingItem.iconRes),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(28.dp).align(Alignment.CenterVertically)
        )
        Text(
            text = settingItem.title, modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(Alignment.CenterVertically), style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SettingHeaderItem(settingItem: SettingItemUIState.SettingHeaderUIState) {
    Text(
        text = settingItem.title, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 0.dp)
            .padding(top = 20.dp),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Preview
@Composable
fun PreviewSettingSwitchItem() {
    SettingSwitchItem(
        settingItem = SettingItemUIState.SwitchUIState(
            id = 1,
            title = "Sound",
            isEnable = true
        )
    )
}

@Preview
@Composable
fun PreviewSettingTextItem() {
    SettingTextItem(
        settingItem = SettingItemUIState.TextUIState(
            id = 1,
            title = "Sound",
            iconRes = R.drawable.ic_gift
        )
    )
}