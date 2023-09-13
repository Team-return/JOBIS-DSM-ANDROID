package team.retum.domain.usecase.student

import team.retum.domain.param.students.EditProfileImageParam
import team.retum.domain.repository.StudentRepository
import javax.inject.Inject

class EditProfileImageUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(editProfileImageParam: EditProfileImageParam) = runCatching {
        studentRepository.editProfileImage(editProfileImageParam = editProfileImageParam)
    }
}