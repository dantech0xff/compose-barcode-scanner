package com.creative.qrcodescanner.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.qrcodescanner.data.entity.QRCodeEntity
import com.creative.qrcodescanner.usecase.GetQRCodeByRowIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by dan on 14/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@HiltViewModel
class QRCodeResultViewModel @Inject constructor(
    private val getQRCodeByRowIdUseCase: GetQRCodeByRowIdUseCase
) : ViewModel() {

    private val _qrCodeResultUIState: MutableStateFlow<QRCodeResultUIState> =
        MutableStateFlow(QRCodeResultUIState.Loading)
    val qrCodeResultUIState: StateFlow<QRCodeResultUIState> = _qrCodeResultUIState

    fun getQRCodeByRowId(rowId: Int) {
        viewModelScope.launch {
            _qrCodeResultUIState.value = QRCodeResultUIState.Loading
            getQRCodeByRowIdUseCase.execute(rowId)
                .catch { throwable ->
                    _qrCodeResultUIState.value = QRCodeResultUIState.Error(throwable)
                }
                .collectLatest {
                    _qrCodeResultUIState.value = if (it != null) QRCodeResultUIState.Success(it) else QRCodeResultUIState.Loading
                }
        }
    }
}

sealed class QRCodeResultUIState {
    data object Loading : QRCodeResultUIState()
    data class Success(val qrCodeResult: QRCodeEntity) : QRCodeResultUIState()
    data class Error(val throwable: Throwable) : QRCodeResultUIState()
}