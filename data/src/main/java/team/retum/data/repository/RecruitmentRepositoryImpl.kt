package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.RecruitmentDataSource
import team.retum.data.remote.response.applications.toEntity
import team.retum.domain.entity.recruitment.RecruitmentsEntity
import team.retum.domain.param.recruitment.FetchRecruitmentListParam
import team.retum.domain.repository.RecruitmentRepository
import javax.inject.Inject

class RecruitmentRepositoryImpl @Inject constructor(
    private val recruitmentDataSource: RecruitmentDataSource,
) : RecruitmentRepository {
    override suspend fun fetchRecruitmentList(
        fetchRecruitmentListParam: FetchRecruitmentListParam
    ): RecruitmentsEntity = recruitmentDataSource.fetchRecruitmentList(
        page = fetchRecruitmentListParam.page,
        code = fetchRecruitmentListParam.code,
        company = fetchRecruitmentListParam.company,
    ).toEntity()

    override suspend fun bookmarkRecruitment(
        recruitmentId: Long,
    ) {
        recruitmentDataSource.bookmarkRecruitment(
            recruitmentId = recruitmentId,
        )
    }
}