package team.retum.domain.usecase.code

import team.retum.domain.param.code.FetchCodesParam
import team.retum.domain.repository.CodeRepository
import javax.inject.Inject

class FetchCodesUseCase @Inject constructor(
    private val codeRepository: CodeRepository,
) {
    suspend operator fun invoke(
        fetchCodesParam: FetchCodesParam,
    ) = runCatching{
        codeRepository.fetchCodes(
            fetchCodesParam = fetchCodesParam,
        )
    }
}