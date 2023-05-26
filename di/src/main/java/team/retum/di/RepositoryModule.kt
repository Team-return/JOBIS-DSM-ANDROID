package team.retum.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import team.retum.data.repository.ApplicationsRepositoryImpl
import team.retum.data.repository.RecruitmentRepositoryImpl
import team.retum.data.repository.StudentsRepositoryImpl
import team.retum.data.repository.UserRepositoryImpl
import team.retum.domain.repository.ApplicationsRepository
import team.retum.domain.repository.RecruitmentRepository
import team.retum.domain.repository.StudentsRepository
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
        applicationsRepositoryImpl: ApplicationsRepositoryImpl,
    ): ApplicationsRepository

    @Binds
    @Singleton
    abstract fun bindsStudentsRepository(
        studentsRepositoryImpl: StudentsRepositoryImpl,
    ): StudentsRepository
}