package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.RecruitmentDataSource
import team.retum.data.remote.response.toEntity
import team.retum.domain.entity.RecruitmentListEntity
import team.retum.domain.param.FetchRecruitmentListParam
import team.retum.domain.repository.RecruitmentRepository
import javax.inject.Inject

class RecruitmentRepositoryImpl @Inject constructor(
    private val recruitmentDataSource: RecruitmentDataSource,
): RecruitmentRepository {
    override suspend fun fetchRecruitmentList(
        fetchRecruitmentListParam: FetchRecruitmentListParam
    ): RecruitmentListEntity = recruitmentDataSource.fetchRecruitmentList(
        page = fetchRecruitmentListParam.key,
        keyword = fetchRecruitmentListParam.keyword,
        company = fetchRecruitmentListParam.companyName,
    ).toEntity()

}