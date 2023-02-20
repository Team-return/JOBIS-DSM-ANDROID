package com.jobis.di

import com.jobis.data.remote.datasource.declaration.UserDataSource
import com.jobis.data.remote.datasource.implementation.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideUserDataSource(
        userDataSourceImpl: UserDataSourceImpl,
        userDataStorageImpl: UserDataSourceImpl,
    ): UserDataSource
}