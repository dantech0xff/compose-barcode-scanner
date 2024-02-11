package com.creative.qrcodescanner.usecase.history

import com.creative.qrcodescanner.data.entity.QRCodeEntity
import com.creative.qrcodescanner.repo.HistoryRepo
import com.creative.qrcodescanner.repo.user.UserDataRepo
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
 constructor(
    private val historyRepo: HistoryRepo,
    private val userDataRepo: UserDataRepo
 ) : BaseFlowUseCase<QRCodeEntity, Long>() {
    override fun execute(input: QRCodeEntity): Flow<Long> {
        return flow {
            if (userDataRepo.isPremium()) {
                input.rawData?.let { historyRepo.deleteQRCodeEntity(it) }
            }
            emit(historyRepo.insertQRCodeEntity(input))
        }
    }
}