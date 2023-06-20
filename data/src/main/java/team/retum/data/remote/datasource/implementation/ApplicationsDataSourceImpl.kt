package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.ApplicationsApi
import team.retum.data.remote.datasource.declaration.ApplicationsDataSource
import team.retum.data.remote.request.application.ApplyCompanyRequest
import team.retum.data.remote.response.applications.FetchAppliedCompanyHistoriesResponse
import team.retum.data.remote.response.applications.FetchTotalPassedStudentCountResponse
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class ApplicationsDataSourceImpl @Inject constructor(
    private val applicationsApi: ApplicationsApi,
) : ApplicationsDataSource {
    override suspend fun fetchTotalPassedStudentCount(): FetchTotalPassedStudentCountResponse =
        HttpHandler<FetchTotalPassedStudentCountResponse>().httpRequest {
            applicationsApi.fetchTotalPassedStudentCount()
        }.sendRequest()

    override suspend fun fetchAppliedCompanyHistories(): FetchAppliedCompanyHistoriesResponse =
        HttpHandler<FetchAppliedCompanyHistoriesResponse>().httpRequest {
            applicationsApi.fetchAppliedCompanyHistories()
        }.sendRequest()

    override suspend fun applyCompany(
        recruitmentId: Long,
        applyCompanyRequest: ApplyCompanyRequest
    ) = HttpHandler<Unit>().httpRequest {
        applicationsApi.applyCompany(
            recruitmentId = recruitmentId,
            applyCompanyRequest = applyCompanyRequest,
        )
    }.sendRequest()
}