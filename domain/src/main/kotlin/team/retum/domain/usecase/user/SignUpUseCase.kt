package team.retum.domain.usecase.user

import team.retum.domain.param.user.SignUpParam
import team.retum.domain.repository.StudentRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(signUpParam: SignUpParam) = runCatching {
        studentRepository.signUp(signUpParam = signUpParam)
    }
}
