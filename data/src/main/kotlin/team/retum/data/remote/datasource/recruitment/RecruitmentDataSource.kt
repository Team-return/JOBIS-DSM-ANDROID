package team.retum.data.remote.datasource.recruitment

import team.retum.data.remote.response.recruitment.FetchRecruitmentCountResponse
import team.retum.data.remote.response.recruitment.FetchRecruitmentDetailsResponse
import team.retum.data.remote.response.recruitment.RecruitmentsResponse

interface RecruitmentDataSource {
    suspend fun fetchRecruitmentList(
        page: Long,
        jobCode: Long?,
        techCode: String?,
        name: String?,
        winterIntern: Boolean,
    ): RecruitmentsResponse

    suspend fun fetchRecruitmentDetails(recruitmentId: Long): FetchRecruitmentDetailsResponse

    suspend fun fetRecruitmentCount(
        name: String?,
        jobCode: Long?,
        techCode: String?,
        winterIntern: Boolean,
    ): FetchRecruitmentCountResponse
}
