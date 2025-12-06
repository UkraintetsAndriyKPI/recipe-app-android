package com.example.recipeandroidapp.di

import android.app.Application
import com.example.recipeandroidapp.data.manager.LocalUserManagerImpl
import com.example.recipeandroidapp.domain.manager.LocalUserManager
import com.example.recipeandroidapp.domain.usecases.AppEntryUseCases
import com.example.recipeandroidapp.domain.usecases.ReadAppEntry
import com.example.recipeandroidapp.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton()
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager),
    )
}