package team.retum.domain.usecase.student

import team.retum.domain.param.students.ResetPasswordParam
import team.retum.domain.repository.StudentRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(resetPasswordParam: ResetPasswordParam) = runCatching {
        studentRepository.resetPassword(resetPasswordParam = resetPasswordParam)
    }
}
