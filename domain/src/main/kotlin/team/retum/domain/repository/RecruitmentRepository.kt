package team.retum.domain.repository

import team.retum.domain.entity.recruitment.RecruitmentCountEntity
import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.domain.entity.recruitment.RecruitmentsEntity
import team.retum.domain.param.recruitment.FetchRecruitmentsParam

interface RecruitmentRepository {
    suspend fun fetchRecruitmentList(fetchRecruitmentsParam: FetchRecruitmentsParam): RecruitmentsEntity

    suspend fun fetchRecruitmentDetails(recruitmentId: Long): RecruitmentDetailsEntity

    suspend fun fetchRecruitmentCount(fetchRecruitmentsParam: FetchRecruitmentsParam): RecruitmentCountEntity
}
