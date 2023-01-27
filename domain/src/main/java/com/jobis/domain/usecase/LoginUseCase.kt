package com.jobis.domain.usecase

import com.jobis.domain.param.LoginParam
import com.jobis.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
) : UseCase<LoginParam, Unit>() {
    override suspend fun execute(data: LoginParam) {
        userRepository.postLogin(data)
    }
}