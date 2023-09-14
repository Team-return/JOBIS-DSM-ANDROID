package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.RecruitmentApi
import team.retum.data.remote.datasource.declaration.RecruitmentDataSource
import team.retum.data.remote.response.recruitment.FetchRecruitmentDetailsResponse
import team.retum.data.remote.response.recruitment.RecruitmentsResponse
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class RecruitmentDataSourceImpl @Inject constructor(
    private val recruitmentApi: RecruitmentApi,
) : RecruitmentDataSource {
    override suspend fun fetchRecruitmentList(
        page: Int,
        jobCode: Long?,
        techCode: String?,
        name: String?
    ): RecruitmentsResponse = HttpHandler<RecruitmentsResponse>().httpRequest {
        recruitmentApi.fetchRecruitmentList(
            page = page,
            jobCode = jobCode,
            techCode = techCode,
            name = name,
        )
    }.sendRequest()

    override suspend fun fetchRecruitmentDetails(recruitmentId: Long) =
        HttpHandler<FetchRecruitmentDetailsResponse>().httpRequest {
            recruitmentApi.fetchRecruitmentDetails(
                recruitmentId = recruitmentId,
            )
        }.sendRequest()

    override suspend fun bookmarkRecruitment(recruitmentId: Long) =
        HttpHandler<Unit>().httpRequest {
            recruitmentApi.bookmarkRecruitment(
                recruitmentId = recruitmentId,
            )
        }.sendRequest()
}