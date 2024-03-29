package team.retum.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.retum.data.repository.ApplicationRepositoryImpl
import team.retum.data.repository.AuthRepositoryImpl
import team.retum.data.repository.BookmarkRepositoryImpl
import team.retum.data.repository.BugRepositoryImpl
import team.retum.data.repository.CodeRepositoryImpl
import team.retum.data.repository.CompanyRepositoryImpl
import team.retum.data.repository.FileRepositoryImpl
import team.retum.data.repository.RecruitmentRepositoryImpl
import team.retum.data.repository.ReviewRepositoryImpl
import team.retum.data.repository.StudentRepositoryImpl
import team.retum.data.repository.UserRepositoryImpl
import team.retum.domain.repository.ApplicationsRepository
import team.retum.domain.repository.AuthRepository
import team.retum.domain.repository.BookmarkRepository
import team.retum.domain.repository.BugRepository
import team.retum.domain.repository.CodeRepository
import team.retum.domain.repository.CompanyRepository
import team.retum.domain.repository.FileRepository
import team.retum.domain.repository.RecruitmentRepository
import team.retum.domain.repository.ReviewRepository
import team.retum.domain.repository.StudentRepository
import team.retum.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindsRecruitmentRepository(
        recruitmentRepositoryImpl: RecruitmentRepositoryImpl,
    ): RecruitmentRepository

    @Binds
    @Singleton
    abstract fun bindsApplicationsRepository(
        applicationRepositoryImpl: ApplicationRepositoryImpl,
    ): ApplicationsRepository

    @Binds
    @Singleton
    abstract fun bindsStudentRepository(
        studentsRepositoryImpl: StudentRepositoryImpl,
    ): StudentRepository

    @Binds
    @Singleton
    abstract fun bindsCompanyRepository(
        companyRepositoryImpl: CompanyRepositoryImpl,
    ): CompanyRepository

    @Binds
    @Singleton
    abstract fun bindsReviewRepository(
        reviewRepositoryImpl: ReviewRepositoryImpl,
    ): ReviewRepository

    @Binds
    @Singleton
    abstract fun bindsCodeRepository(
        codeRepositoryImpl: CodeRepositoryImpl,
    ): CodeRepository

    @Binds
    @Singleton
    abstract fun bindsBookmarkRepository(
        bookmarkRepositoryImpl: BookmarkRepositoryImpl,
    ): BookmarkRepository

    @Binds
    @Singleton
    abstract fun bindsFileRepository(
        fileRepositoryImpl: FileRepositoryImpl,
    ): FileRepository

    @Binds
    @Singleton
    abstract fun bindsBugRepository(
        bugRepositoryImpl: BugRepositoryImpl,
    ): BugRepository

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}
