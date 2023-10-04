package team.retum.domain.usecase.recruitment

import team.retum.domain.param.recruitment.FetchRecruitmentListParam
import team.retum.domain.repository.RecruitmentRepository
import javax.inject.Inject

class FetchRecruitmentCountUseCase @Inject constructor(
    private val recruitmentRepository: RecruitmentRepository,
) {
    suspend operator fun invoke(
        fetchRecruitmentListParam: FetchRecruitmentListParam,
    ) = runCatching {
        recruitmentRepository.fetchRecruitmentCount(
            fetchRecruitmentListParam = fetchRecruitmentListParam,
        )
    }
}