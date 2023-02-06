package com.jobis.jobis_android.event

sealed class LoginEvent {
    object MoveToMainActivity : LoginEvent()
    object UnAuthorization : LoginEvent()
    object NotFound : LoginEvent()
}