package com.creative.qrcodescanner.di.module

import android.content.Context
import com.creative.qrcodescanner.data.QRAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by dan on 11/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideQRCodeEntityDAO(qrAppDatabase: QRAppDatabase) = qrAppDatabase.qrCodeEntityDAO()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): QRAppDatabase {
        return QRAppDatabase.invoke(appContext)
    }
}