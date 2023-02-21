package com.jobis.di

import com.jobis.data.storage.UserDataStorage
import com.jobis.data.storage.UserDataStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStorageModule {

    @Binds
    @Singleton
    abstract fun provideUserDataStorage(
        userDataStorageImpl: UserDataStorageImpl,
    ): UserDataStorage
}