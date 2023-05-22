package team.retum.domain.repository

import team.retum.domain.entity.RecruitmentListEntity
import team.retum.domain.param.FetchRecruitmentListParam

interface RecruitmentRepository {
    suspend fun fetchRecruitmentList(
        fetchRecruitmentListParam: FetchRecruitmentListParam,
    ): RecruitmentListEntity
}