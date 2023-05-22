package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.RecruitmentListResponse

interface RecruitmentDataSource {
    suspend fun fetchRecruitmentList(
        page: Int,
        keyword: String?,
        company: String?,
    ): RecruitmentListResponse
}