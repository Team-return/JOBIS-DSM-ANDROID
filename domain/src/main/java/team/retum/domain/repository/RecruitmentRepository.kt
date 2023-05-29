package team.retum.domain.repository

import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.domain.entity.recruitment.RecruitmentsEntity
import team.retum.domain.param.recruitment.FetchRecruitmentListParam

interface RecruitmentRepository {
    suspend fun fetchRecruitmentList(
        fetchRecruitmentListParam: FetchRecruitmentListParam,
    ): RecruitmentsEntity

    suspend fun bookmarkRecruitment(
        recruitmentId: Long,
    )

    suspend fun fetchRecruitmentDetails(
        recruitmentId: Long,
    ): RecruitmentDetailsEntity
}