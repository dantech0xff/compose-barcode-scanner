package com.creative.qrcodescanner.ui.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.shadow
import com.creative.qrcodescanner.ui.theme.QRCodeScannerTheme
import com.creative.qrcodescanner.usecase.history.QRCodeItemUIState

/**
 * Created by dan on 21/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */
 
@Composable
@Stable
fun QRCodeHistoryItemUI(
    modifier: Modifier,
    itemUiState: QRCodeItemUIState,
    onDelete: ((item: QRCodeItemUIState) -> Unit)? = null,
    onClick: (() -> Unit)? = null) {
    Box(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 8.dp, vertical = 4.dp)
        .shadow(
            Color(0x901c1c1c).copy(alpha = 0.2f), blurRadius = 5.dp,
            borderRadius = 8.dp, spread = 0.dp, offsetY = 0.dp, offsetX = 0.dp
        )
        .background(MaterialTheme.colorScheme.inversePrimary, shape = RoundedCornerShape(8.dp))
        .clip(RoundedCornerShape(8.dp))
        .clickable {
            onClick?.invoke()
        }
        .padding(12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_qr_white),
                contentDescription = stringResource(id = R.string.qr_code_history),
                modifier = Modifier
                    .size(56.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight().padding(end = 24.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = itemUiState.rawData, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = itemUiState.dateString, color = MaterialTheme.colorScheme.inverseSurface,
                    style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Light
                )
            }
        }
        Image(
            painter = painterResource(R.drawable.delete),
            contentDescription = stringResource(id = R.string.qr_code_history),
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .clickable {
                    onDelete?.invoke(itemUiState)
                }
        )
    }
}

@Preview
@Composable
fun QRCodeHistoryItemUIPreview() {
    QRCodeScannerTheme {
        QRCodeHistoryItemUI(
            Modifier, QRCodeItemUIState(
                id = 1,
                rawData = "https://www.google.comhttps://www.google.comhttps://www.google.comhttps://www.google.comhttps://www.google.com",
                type = 1,
                dateString = "2021-01-01 12:00:00",
                isFavorite = false,
                isScanned = true
            )
        )
    }
}