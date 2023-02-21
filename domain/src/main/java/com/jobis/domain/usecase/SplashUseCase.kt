package com.jobis.domain.usecase

import com.jobis.domain.param.LoginParam
import com.jobis.domain.repository.UserRepository
import javax.inject.Inject

class SplashUseCase @Inject constructor(
    private val userRepository: UserRepository,
): UseCase<Unit, LoginParam>() {
    override suspend fun execute(data: Unit): LoginParam =
        userRepository.fetchUserInfo()
}