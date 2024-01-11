package com.creative.qrcodescanner.repo

import com.creative.qrcodescanner.data.entity.QRCodeEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by dan on 11/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

interface HistoryRepo {
    suspend fun getQRCodeHistory(): List<QRCodeEntity>
    fun getQRCOdeHistoryFlow(): Flow<List<QRCodeEntity>>
}