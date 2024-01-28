package com.creative.qrcodescanner.usecase

import com.creative.qrcodescanner.repo.HistoryRepo
import com.creative.qrcodescanner.usecase.base.BaseFlowUseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by dan on 11/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@ViewModelScoped
class GetQRCodeHistoryFlowUseCase @Inject
    constructor(private val historyRepo: HistoryRepo) : BaseFlowUseCase<Unit, QRCodeHistoryUIState>() {
        private var isFirstLoad = true
    override fun execute(input: Unit): Flow<QRCodeHistoryUIState> {
        return historyRepo.getQRCOdeHistoryFlow().map {
            if(isFirstLoad) {
                isFirstLoad = false
                delay(Random(Calendar.getInstance().timeInMillis).nextLong(1200))
            }
            if (it.isEmpty()) {
                QRCodeHistoryUIState.Empty
            } else {
                QRCodeHistoryUIState.Success(
                    data = it.map { qrCodeEntity ->
                        QRCodeItemUIState(
                            id = qrCodeEntity.id,
                            rawData = qrCodeEntity.rawData.orEmpty(),
                            type = qrCodeEntity.qrType,
                            dateString = SimpleDateFormat("HH:mm, E dd MMM, yyyy", Locale.getDefault()).format(Date(qrCodeEntity.scanDateTimeMillis)).toString(),
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