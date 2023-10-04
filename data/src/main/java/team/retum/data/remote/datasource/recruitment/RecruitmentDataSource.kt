package team.retum.data.remote.datasource.recruitment

import team.retum.data.remote.response.recruitment.FetchRecruitmentCountResponse
import team.retum.data.remote.response.recruitment.FetchRecruitmentDetailsResponse
import team.retum.data.remote.response.recruitment.RecruitmentsResponse

interface RecruitmentDataSource {
    suspend fun fetchRecruitmentList(
        page: Int,
        jobCode: Long?,
        techCode: String?,
        name: String?,
    ): RecruitmentsResponse

    suspend fun fetchRecruitmentDetails(recruitmentId: Long): FetchRecruitmentDetailsResponse

    suspend fun fetRecruitmentCount(
        page: Int,
        name: String?,
    ): FetchRecruitmentCountResponse
}