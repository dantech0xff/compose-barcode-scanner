package com.creative.qrcodescanner.repo

import com.creative.qrcodescanner.data.dao.QRCodeEntityDAO
import com.creative.qrcodescanner.data.entity.QRCodeEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by dan on 11/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class HistoryRepoImpl(private val qrCodeEntityDAO: QRCodeEntityDAO) : HistoryRepo {
    override suspend fun getQRCodeHistory(): List<QRCodeEntity> {
        return qrCodeEntityDAO.getAllQRCodeEntity()
    }

    override fun getQRCOdeHistoryFlow(): Flow<List<QRCodeEntity>> {
        return qrCodeEntityDAO.getAllQRCodeEntityFlow()
    }
}