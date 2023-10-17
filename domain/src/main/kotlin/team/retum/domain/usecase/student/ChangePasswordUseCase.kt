package team.retum.domain.usecase.student

import team.retum.domain.param.students.ChangePasswordParam
import team.retum.domain.repository.StudentRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        changePasswordParam: ChangePasswordParam,
    ) = runCatching {
        studentRepository.changePassword(
            changePasswordParam = changePasswordParam,
        )
    }
}
