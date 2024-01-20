package com.creative.qrcodescanner.usecase

import com.creative.qrcodescanner.repo.user.UserDataRepo
import com.creative.qrcodescanner.usecase.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by dan on 20/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

class UpdateVibrateSettingUseCase @Inject constructor(
    private val userDataRepo: UserDataRepo
) : BaseUseCase<Boolean, Unit>() {
    override suspend fun execute(input: Boolean) {
        userDataRepo.updateVibrateSetting(input)
    }
}