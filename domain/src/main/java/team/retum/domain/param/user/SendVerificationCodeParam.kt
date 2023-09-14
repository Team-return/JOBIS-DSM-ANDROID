package team.retum.domain.param.user

import team.retum.domain.enums.AuthCodeType

data class SendVerificationCodeParam(
    val email: String,
    val authCodeType: AuthCodeType,
)
