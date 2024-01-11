package com.creative.qrcodescanner.di.module

import com.creative.qrcodescanner.data.dao.QRCodeEntityDAO
import com.creative.qrcodescanner.repo.HistoryRepo
import com.creative.qrcodescanner.repo.HistoryRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideHistoryRepo(qrCodeEntityDAO: QRCodeEntityDAO): HistoryRepo = HistoryRepoImpl(qrCodeEntityDAO)

}