package team.retum.domain.usecase.student

import team.retum.domain.repository.StudentRepository
import javax.inject.Inject

class FetchStudentInformationUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke() = runCatching {
        studentRepository.fetchStudentInformation()
    }
}
