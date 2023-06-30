package team.retum.domain.usecase.student

import team.retum.domain.param.students.ResetPasswordParam
import team.retum.domain.repository.StudentsRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository,
){
    suspend operator fun invoke(
        resetPasswordParam: ResetPasswordParam,
    ) = runCatching{
        studentsRepository.resetPassword(
            resetPasswordParam = resetPasswordParam,
        )
    }
}