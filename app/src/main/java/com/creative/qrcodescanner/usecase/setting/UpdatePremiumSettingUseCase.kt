package com.creative.qrcodescanner.usecase.setting

import com.creative.qrcodescanner.repo.user.UserDataRepo
import com.creative.qrcodescanner.usecase.base.BaseUseCase
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

/**
 * Created by dan on 11/02/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@ViewModelScoped
class UpdatePremiumSettingUseCase @Inject constructor(
    private val userDataRepo: UserDataRepo
) : BaseUseCase<Boolean, Unit>() {
    override suspend fun execute(input: Boolean) {
        userDataRepo.updatePremiumSetting(input)
    }
}