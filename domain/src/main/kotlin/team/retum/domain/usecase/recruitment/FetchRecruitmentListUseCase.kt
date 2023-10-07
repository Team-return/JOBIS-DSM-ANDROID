package team.retum.domain.usecase.recruitment

import team.retum.domain.param.recruitment.FetchRecruitmentListParam
import team.retum.domain.repository.RecruitmentRepository
import javax.inject.Inject

class FetchRecruitmentListUseCase @Inject constructor(
    private val recruitmentRepository: RecruitmentRepository,
) {
    suspend operator fun invoke(
        fetchRecruitmentListParam: FetchRecruitmentListParam,
    ) = runCatching {
        recruitmentRepository.fetchRecruitmentList(
            fetchRecruitmentListParam = fetchRecruitmentListParam,
        )
    }
}
