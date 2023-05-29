package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.RecruitmentApi
import team.retum.data.remote.datasource.declaration.RecruitmentDataSource
import team.retum.data.remote.response.recruitment.RecruitmentsResponse
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class RecruitmentDataSourceImpl @Inject constructor(
    private val recruitmentApi: RecruitmentApi,
) : RecruitmentDataSource {
    override suspend fun fetchRecruitmentList(
        page: Int,
        code: Long?,
        company: String?
    ): RecruitmentsResponse = HttpHandler<RecruitmentsResponse>().httpRequest {
        recruitmentApi.fetchRecruitmentList(
            page = page,
            code = code,
            company = company,
        )
    }.sendRequest()

    override suspend fun bookmarkRecruitment(
        recruitmentId: Long,
    ) = HttpHandler<Unit>().httpRequest {
        recruitmentApi.bookmarkRecruitment(
            recruitmentId = recruitmentId,
        )
    }.sendRequest()
}