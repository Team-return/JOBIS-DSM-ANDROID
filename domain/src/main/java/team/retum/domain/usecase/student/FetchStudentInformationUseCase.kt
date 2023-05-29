package team.retum.domain.usecase.student

import team.retum.domain.repository.StudentsRepository
import javax.inject.Inject

class FetchStudentInformationUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository,
) {
    suspend operator fun invoke() = runCatching{
        studentsRepository.fetchStudentInformation()
    }
}