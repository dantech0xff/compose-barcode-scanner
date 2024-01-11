package com.creative.qrcodescanner.usecase

import com.creative.qrcodescanner.data.entity.QRCodeEntity
import com.creative.qrcodescanner.repo.HistoryRepo
import com.creative.qrcodescanner.usecase.base.BaseUseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

/**
 * Created by dan on 11/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@ViewModelScoped
class GetQRCodeHistoryUseCase @Inject
    constructor(private val historyRepo: HistoryRepo) : BaseUseCase<Unit, QRCodeHistoryUIState>() {
    override fun execute(input: Unit): Flow<QRCodeHistoryUIState> {
        return historyRepo.getQRCOdeHistoryFlow().map {
            if (it.isEmpty()) {
                QRCodeHistoryUIState.Empty
            } else {
                QRCodeHistoryUIState.Success(
                    data = it.map { qrCodeEntity ->
                        QRCodeItemUIState(
                            id = qrCodeEntity.id,
                            rawData = qrCodeEntity.rawData.orEmpty(),
                            type = qrCodeEntity.qrType,
                            dateString = Date(qrCodeEntity.scanDateTimeMillis).toString(),
                            isFavorite = qrCodeEntity.isFavorite,
                            isScanned = qrCodeEntity.isScanned
                        )
                    }
                )
            }
        }.catch {
            emit(QRCodeHistoryUIState.Error(it))
        }
    }
}

sealed class QRCodeHistoryUIState {
    data object Loading : QRCodeHistoryUIState()
    data class Success(
        val data: List<QRCodeItemUIState>
    ) : QRCodeHistoryUIState()

    data object Empty : QRCodeHistoryUIState()
    class Error(val throwable: Throwable) : QRCodeHistoryUIState()
}

data class QRCodeItemUIState(
    val id: Int,
    val rawData: String,
    val type: Int,
    val dateString: String,
    val isFavorite: Boolean,
    val isScanned: Boolean
)