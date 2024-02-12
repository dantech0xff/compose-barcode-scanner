package com.creative.qrcodescanner.ui.premium

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creative.qrcodescanner.R
import com.creative.qrcodescanner.ui.theme.QRCodeScannerTheme

/**
 * Created by dan on 03/02/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */
 
@Composable
fun BenefitRow(@DrawableRes iconRes: Int, textStr: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null, modifier = Modifier.size(20.dp)
        )
        Text(
            text = textStr, modifier = Modifier,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
fun BenefitRowPreview() {
    QRCodeScannerTheme {
        BenefitRow(R.drawable.icon_email, stringResource(id = R.string.qr_code_history ))
    }
}
