package com.creative.qrcodescanner.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.creative.qrcodescanner.data.entity.UserSettingData
import com.creative.qrcodescanner.data.entity.userSettingPreferences
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideUserSettingPref(@ApplicationContext context: Context): DataStore<Preferences> = context.userSettingPreferences
}