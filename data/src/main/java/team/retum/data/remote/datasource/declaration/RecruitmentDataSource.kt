package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.RecruitmentsResponse

interface RecruitmentDataSource {
    suspend fun fetchRecruitmentList(
        page: Int,
        code: Long?,
        company: String?,
    ): RecruitmentsResponse

    suspend fun bookmarkRecruitment(
        recruitmentId: Long,
    )
}