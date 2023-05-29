package team.retum.domain.repository

import team.retum.domain.entity.RecruitmentsEntity
import team.retum.domain.param.FetchRecruitmentListParam

interface RecruitmentRepository {
    suspend fun fetchRecruitmentList(
        fetchRecruitmentListParam: FetchRecruitmentListParam,
    ): RecruitmentsEntity

    suspend fun bookmarkRecruitment(
        recruitmentId: Long,
    )
}