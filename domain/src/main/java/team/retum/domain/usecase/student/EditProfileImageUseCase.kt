package team.retum.domain.usecase.student

import team.retum.domain.param.students.EditProfileImageParam
import team.retum.domain.repository.StudentsRepository
import javax.inject.Inject

class EditProfileImageUseCase @Inject constructor(
    private val studentsRepository: StudentsRepository,
) {
    suspend operator fun invoke(editProfileImageParam: EditProfileImageParam) = runCatching {
        studentsRepository.editProfileImage(editProfileImageParam = editProfileImageParam)
    }
}