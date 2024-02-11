package com.creative.qrcodescanner.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.qrcodescanner.usecase.history.DeleteQRCodeHistoryUseCase
import com.creative.qrcodescanner.usecase.history.GetQRCodeHistoryFlowUseCase
import com.creative.qrcodescanner.usecase.history.QRCodeHistoryUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by dan on 11/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getQRCodeHistoryUseCase: GetQRCodeHistoryFlowUseCase,
    private val deleteQRCodeHistoryUseCase: DeleteQRCodeHistoryUseCase
) : ViewModel() {
    val qrCodeHistoryUIState: StateFlow<QRCodeHistoryUIState> = getQRCodeHistoryUseCase.execute(Unit).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = QRCodeHistoryUIState.Loading
    )


    fun deleteQRCodeHistory(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteQRCodeHistoryUseCase.execute(id)
        }
    }
}