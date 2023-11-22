package team.retum.data.remote.datasource.recruitment

import team.retum.data.remote.api.RecruitmentApi
import team.retum.data.remote.response.recruitment.FetchRecruitmentCountResponse
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
        name: String?,
        winterIntern: Boolean,
    ): RecruitmentsResponse = HttpHandler<RecruitmentsResponse>().httpRequest {
        recruitmentApi.fetchRecruitmentList(
            page = page,
            jobCode = jobCode,
            techCode = techCode,
            name = name,
            winterIntern = winterIntern,
        )
    }.sendRequest()

    override suspend fun fetchRecruitmentDetails(recruitmentId: Long) =
        HttpHandler<FetchRecruitmentDetailsResponse>().httpRequest {
            recruitmentApi.fetchRecruitmentDetails(recruitmentId)
        }.sendRequest()

    override suspend fun fetRecruitmentCount(
        name: String?,
        jobCode: Long?,
        techCode: String?,
        winterIntern: Boolean,
    ): FetchRecruitmentCountResponse = HttpHandler<FetchRecruitmentCountResponse>().httpRequest {
        recruitmentApi.fetchRecruitmentCount(
            name = name,
            jobCode = jobCode,
            techCode = techCode,
            winterIntern = winterIntern,
        )
    }.sendRequest()
}
