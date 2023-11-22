package team.retum.data.repository

import team.retum.data.remote.datasource.recruitment.RecruitmentDataSource
import team.retum.data.remote.response.recruitment.toEntity
import team.retum.domain.entity.recruitment.RecruitmentCountEntity
import team.retum.domain.entity.recruitment.RecruitmentDetailsEntity
import team.retum.domain.param.recruitment.FetchRecruitmentsParam
import team.retum.domain.repository.RecruitmentRepository
import javax.inject.Inject

class RecruitmentRepositoryImpl @Inject constructor(
    private val recruitmentDataSource: RecruitmentDataSource,
) : RecruitmentRepository {
    override suspend fun fetchRecruitmentList(fetchRecruitmentsParam: FetchRecruitmentsParam) =
        with(fetchRecruitmentsParam) {
            recruitmentDataSource.fetchRecruitmentList(
                page = page,
                jobCode = jobCode,
                techCode = techCode,
                name = name,
                winterIntern = winterIntern,
            ).toEntity()
        }

    override suspend fun fetchRecruitmentDetails(recruitmentId: Long): RecruitmentDetailsEntity =
        recruitmentDataSource.fetchRecruitmentDetails(recruitmentId).toEntity()

    override suspend fun fetchRecruitmentCount(
        fetchRecruitmentsParam: FetchRecruitmentsParam,
    ): RecruitmentCountEntity = recruitmentDataSource.fetRecruitmentCount(
        name = fetchRecruitmentsParam.name,
        jobCode = fetchRecruitmentsParam.jobCode,
        techCode = fetchRecruitmentsParam.techCode,
        winterIntern = fetchRecruitmentsParam.winterIntern,
    ).toEntity()
}
