package com.creative.qrcodescanner.usecase.history

import com.creative.qrcodescanner.repo.HistoryRepo
import com.creative.qrcodescanner.usecase.base.BaseUseCase
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * Created by dan on 21/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@ViewModelScoped
class DeleteQRCodeHistoryUseCase @Inject constructor(
    private val qrCodeHistoryRepo: HistoryRepo
) : BaseUseCase<Int, Unit>() {
    override suspend fun execute(input: Int) {
        qrCodeHistoryRepo.deleteQRCodeEntity(input)
    }
}