package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.ApplicationsDataSource
import team.retum.data.remote.response.applications.toEntity
import team.retum.domain.entity.applications.AppliedCompanyHistoriesEntity
import team.retum.domain.entity.applications.StudentCountsEntity
import team.retum.domain.repository.ApplicationsRepository
import javax.inject.Inject

class ApplicationsRepositoryImpl @Inject constructor(
    private val applicationsDataSource: ApplicationsDataSource,
): ApplicationsRepository {
    override suspend fun fetchPassedStudentCount(): StudentCountsEntity =
        applicationsDataSource.fetchTotalPassedStudentCount().toEntity()

    override suspend fun fetchAppliedCompanyHistories(): AppliedCompanyHistoriesEntity =
        applicationsDataSource.fetchAppliedCompanyHistories().toEntity()
}