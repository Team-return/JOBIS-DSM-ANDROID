package team.retum.data.repository

import team.retum.data.remote.datasource.application.ApplicationDataSource
import team.retum.data.remote.request.application.toRequest
import team.retum.data.remote.response.application.toEntity
import team.retum.domain.entity.applications.AppliedCompanyHistoriesEntity
import team.retum.domain.entity.applications.StudentCountsEntity
import team.retum.domain.param.application.ApplyCompanyParam
import team.retum.domain.repository.ApplicationsRepository
import javax.inject.Inject

class ApplicationRepositoryImpl @Inject constructor(
    private val applicationDataSource: ApplicationDataSource,
) : ApplicationsRepository {
    override suspend fun fetchPassedStudentCount(): StudentCountsEntity =
        applicationDataSource.fetchTotalPassedStudentCount().toEntity()

    override suspend fun fetchAppliedCompanyHistories(): AppliedCompanyHistoriesEntity =
        applicationDataSource.fetchAppliedCompanyHistories().toEntity()

    override suspend fun applyCompany(
        recruitmentId: Long,
        applyCompanyParam: ApplyCompanyParam,
    ) = applicationDataSource.applyCompany(
        recruitmentId = recruitmentId,
        applyCompanyRequest = applyCompanyParam.toRequest(),
    )

    override suspend fun reApplyCompany(
        applicationId: Long,
        applyCompanyParam: ApplyCompanyParam,
    ) = applicationDataSource.reApplyCompany(
        applicationId = applicationId,
        applyCompanyRequest = applyCompanyParam.toRequest(),
    )
}
