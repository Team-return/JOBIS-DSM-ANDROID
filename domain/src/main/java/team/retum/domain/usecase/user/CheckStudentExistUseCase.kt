package team.retum.domain.usecase.user

import team.retum.domain.param.user.CheckStudentExistsParam
import team.retum.domain.repository.UserRepository
import javax.inject.Inject

class CheckStudentExistUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(
        checkStudentExistsParam: CheckStudentExistsParam,
    ) = kotlin.runCatching {
        userRepository.checkStudentExists(
            checkStudentExistsParam = checkStudentExistsParam,
        )
    }
}