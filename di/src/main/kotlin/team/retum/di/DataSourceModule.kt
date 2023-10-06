package team.retum.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.retum.data.remote.datasource.application.ApplicationDataSource
import team.retum.data.remote.datasource.auth.AuthDataSource
import team.retum.data.remote.datasource.bookmark.BookmarkDataSource
import team.retum.data.remote.datasource.bug.BugDataSource
import team.retum.data.remote.datasource.code.CodeDataSource
import team.retum.data.remote.datasource.company.CompanyDataSource
import team.retum.data.remote.datasource.file.FileDataSource
import team.retum.data.remote.datasource.recruitment.RecruitmentDataSource
import team.retum.data.remote.datasource.review.ReviewDataSource
import team.retum.data.remote.datasource.student.StudentsDataSource
import team.retum.data.remote.datasource.user.UserDataSource
import team.retum.data.remote.datasource.application.ApplicationDataSourceImpl
import team.retum.data.remote.datasource.auth.AuthDataSourceImpl
import team.retum.data.remote.datasource.bookmark.BookmarkDataSourceImpl
import team.retum.data.remote.datasource.bug.BugDataSourceImpl
import team.retum.data.remote.datasource.code.CodeDataSourceImpl
import team.retum.data.remote.datasource.company.CompanyDataSourceImpl
import team.retum.data.remote.datasource.file.FileDataSourceImpl
import team.retum.data.remote.datasource.recruitment.RecruitmentDataSourceImpl
import team.retum.data.remote.datasource.review.ReviewDataSourceImpl
import team.retum.data.remote.datasource.student.StudentsDataSourceImpl
import team.retum.data.remote.datasource.user.UserDataSourceImpl
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
        applicationsDataSourceImpl: ApplicationDataSourceImpl,
    ): ApplicationDataSource

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

    @Binds
    @Singleton
    abstract fun bindCodeDataSource(
        codeDataSourceImpl: CodeDataSourceImpl,
    ): CodeDataSource

    @Binds
    @Singleton
    abstract fun bindBookmarkDataSource(
        bookmarkDataSourceImpl: BookmarkDataSourceImpl,
    ): BookmarkDataSource

    @Binds
    @Singleton
    abstract fun bindFileDataSource(
        fileDataSourceImpl: FileDataSourceImpl,
    ): FileDataSource

    @Binds
    @Singleton
    abstract fun bindBugDataSource(
        bugDataSourceImpl: BugDataSourceImpl,
    ): BugDataSource

    @Binds
    @Singleton
    abstract fun bindsAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource
}