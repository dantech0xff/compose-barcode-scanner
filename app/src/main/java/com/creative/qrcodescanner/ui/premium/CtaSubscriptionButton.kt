package com.creative.qrcodescanner.ui.premium

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creative.qrcodescanner.ui.main.roundCorner
import com.creative.qrcodescanner.ui.shadow
import com.creative.qrcodescanner.ui.theme.QRCodeScannerTheme

/**
 * Created by dan on 04/02/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */
 
@Composable
fun CtaSubscriptionButton(title: String, subTitle: String, onClick: (Unit) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 1.dp)
            .shadow(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                blurRadius = 12.dp, borderRadius = 0.dp, spread = 0.dp,
                offsetY = 1.dp, offsetX = 0.dp)
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onClick.invoke(Unit)
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
        Text(text = subTitle, style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun CtaSubscriptionButtonPreview() {
    QRCodeScannerTheme {
        CtaSubscriptionButton("3-days free trial, Paid Yearly",
            "Only 20.99$ per year after trial")
    }
}