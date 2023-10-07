package team.retum.domain.usecase.student

import team.retum.domain.repository.StudentRepository
import javax.inject.Inject

class ComparePasswordUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        password: String,
    ) = runCatching {
        studentRepository.comparePassword(password = password)
    }
}
