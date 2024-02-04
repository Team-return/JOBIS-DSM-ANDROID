package team.retum.domain.usecase.user

import team.retum.domain.param.user.CheckStudentExistsParam
import team.retum.domain.repository.StudentRepository
import javax.inject.Inject

class CheckStudentExistUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(checkStudentExistsParam: CheckStudentExistsParam) = runCatching {
        studentRepository.checkStudentExists(checkStudentExistsParam = checkStudentExistsParam)
    }
}
