package team.retum.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.retum.data.remote.datasource.declaration.ApplicationsDataSource
import team.retum.data.remote.datasource.declaration.CompanyDataSource
import team.retum.data.remote.datasource.declaration.RecruitmentDataSource
import team.retum.data.remote.datasource.declaration.ReviewDataSource
import team.retum.data.remote.datasource.declaration.StudentsDataSource
import team.retum.data.remote.datasource.declaration.UserDataSource
import team.retum.data.remote.datasource.implementation.ApplicationsDataSourceImpl
import team.retum.data.remote.datasource.implementation.CompanyDataSourceImpl
import team.retum.data.remote.datasource.implementation.RecruitmentDataSourceImpl
import team.retum.data.remote.datasource.implementation.ReviewDataSourceImpl
import team.retum.data.remote.datasource.implementation.StudentsDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun bindsApplicationsDataSource(
        applicationsDataSourceImpl: ApplicationsDataSourceImpl,
    ): ApplicationsDataSource

    @Binds
    @Singleton
    abstract fun bindsStudentsDataSource(
        studentsDataSourceImpl: StudentsDataSourceImpl,
    ): StudentsDataSource

    @Binds
    @Singleton
    abstract fun bindsCompanyDataSource(
        companyDataSourceImpl: CompanyDataSourceImpl,
    ): CompanyDataSource

    @Binds
    @Singleton
    abstract fun bindReviewDataSource(
        reviewDataSourceImpl: ReviewDataSourceImpl,
    ): ReviewDataSource
}