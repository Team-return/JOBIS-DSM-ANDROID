package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.recruitment.FetchRecruitmentDetailsResponse
import team.retum.data.remote.response.recruitment.RecruitmentsResponse

interface RecruitmentDataSource {
    suspend fun fetchRecruitmentList(
        page: Int,
        code: Long?,
        company: String?,
    ): RecruitmentsResponse

    suspend fun bookmarkRecruitment(
        recruitmentId: Long,
    )

    suspend fun fetchRecruitmentDetails(
        recruitmentId: Long,
    ): FetchRecruitmentDetailsResponse
}