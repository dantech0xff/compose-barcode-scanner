package com.creative.qrcodescanner.usecase

import com.creative.qrcodescanner.data.entity.QRCodeEntity
import com.creative.qrcodescanner.repo.HistoryRepo
import com.creative.qrcodescanner.usecase.base.BaseUseCase
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
class GetQRCodeByRowIdUseCase @Inject
 constructor(private val historyRepo: HistoryRepo) : BaseUseCase<Int, QRCodeEntity?>() {
    override fun execute(input: Int): Flow<QRCodeEntity?> {
        return flow {
            historyRepo.getQRCodeEntityById(input)?.let {
                emit(it)
            } ?: emit(null)
        }
    }
}