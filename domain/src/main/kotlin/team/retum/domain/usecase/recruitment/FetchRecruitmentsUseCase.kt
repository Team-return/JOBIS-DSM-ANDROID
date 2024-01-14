package team.retum.domain.usecase.recruitment

import team.retum.domain.param.recruitment.FetchRecruitmentsParam
import team.retum.domain.repository.RecruitmentRepository
import javax.inject.Inject

class FetchRecruitmentsUseCase @Inject constructor(
    private val recruitmentRepository: RecruitmentRepository,
) {
    suspend operator fun invoke(fetchRecruitmentsParam: FetchRecruitmentsParam) = runCatching {
        recruitmentRepository.fetchRecruitmentList(fetchRecruitmentsParam = fetchRecruitmentsParam)
    }
}
