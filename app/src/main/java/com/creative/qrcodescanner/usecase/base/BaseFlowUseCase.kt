package com.creative.qrcodescanner.usecase.base

import kotlinx.coroutines.flow.Flow

/**
 * Created by dan on 11/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

abstract class BaseFlowUseCase<In,Out> {
    abstract fun execute(input: In): Flow<Out>
}