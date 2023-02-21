package com.jobis.di

import com.jobis.data.remote.datasource.declaration.UserDataSource
import com.jobis.data.remote.datasource.implementation.UserDataSourceImpl
import com.jobis.data.storage.UserDataStorage
import com.jobis.data.storage.UserDataStorageImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideUserDataSource(
        userDataSourceImpl: UserDataSourceImpl,
    ): UserDataSource
}