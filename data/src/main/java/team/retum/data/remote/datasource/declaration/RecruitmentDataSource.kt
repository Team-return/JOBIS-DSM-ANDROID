package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.RecruitmentListResponse

interface RecruitmentDataSource {
    suspend fun fetchRecruitmentList(
        page: Int,
        code: Long?,
        company: String?,
    ): RecruitmentListResponse
}