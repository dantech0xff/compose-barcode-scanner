package com.creative.qrcodescanner.di.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.creative.qrcodescanner.data.dao.QRCodeEntityDAO
import com.creative.qrcodescanner.repo.HistoryRepo
import com.creative.qrcodescanner.repo.HistoryRepoImpl
import com.creative.qrcodescanner.repo.user.UserDataRepo
import com.creative.qrcodescanner.repo.user.UserDataRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun provideHistoryRepo(qrCodeEntityDAO: QRCodeEntityDAO): HistoryRepo = HistoryRepoImpl(qrCodeEntityDAO)

    @Singleton
    @Provides
    fun provideUserDataRepo(userSettingPref: DataStore<Preferences>): UserDataRepo = UserDataRepoImpl(userSettingPref)
}