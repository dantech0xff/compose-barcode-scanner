package com.creative.qrcodescanner.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.qrcodescanner.usecase.GetQRCodeHistoryUseCase
import com.creative.qrcodescanner.usecase.QRCodeHistoryUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Created by dan on 11/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getQRCodeHistoryUseCase: GetQRCodeHistoryUseCase
) : ViewModel() {
    val qrCodeHistoryUIState: StateFlow<QRCodeHistoryUIState> = getQRCodeHistoryUseCase.execute(Unit).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = QRCodeHistoryUIState.Loading
    )
}