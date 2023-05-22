package team.retum.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.retum.data.remote.datasource.declaration.RecruitmentDataSource
import team.retum.data.remote.datasource.declaration.UserDataSource
import team.retum.data.remote.datasource.implementation.RecruitmentDataSourceImpl
import team.retum.data.remote.datasource.implementation.UserDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideUserDataSource(
        userDataSourceImpl: UserDataSourceImpl,
    ): UserDataSource

    @Binds
    @Singleton
    abstract fun bindsRecruitmentDataSource(
        recruitmentDataSourceImpl: RecruitmentDataSourceImpl,
    ): RecruitmentDataSource
}