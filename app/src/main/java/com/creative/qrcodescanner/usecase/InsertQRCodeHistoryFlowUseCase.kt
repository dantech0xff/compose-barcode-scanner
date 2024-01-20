package com.creative.qrcodescanner.usecase

import com.creative.qrcodescanner.data.entity.QRCodeEntity
import com.creative.qrcodescanner.repo.HistoryRepo
import com.creative.qrcodescanner.usecase.base.BaseFlowUseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by dan on 14/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@ViewModelScoped
class InsertQRCodeHistoryFlowUseCase @Inject
 constructor(private val historyRepo: HistoryRepo) : BaseFlowUseCase<QRCodeEntity, Long>() {
    override fun execute(input: QRCodeEntity): Flow<Long> {
        return flow {
            emit(historyRepo.insertQRCodeEntity(input))
        }
    }
}