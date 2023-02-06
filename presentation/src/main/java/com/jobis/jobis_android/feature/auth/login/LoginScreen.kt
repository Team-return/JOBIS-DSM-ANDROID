package com.jobis.jobis_android.feature.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.event.LoginEvent
import com.jobis.jobis_android.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
){
    LaunchedEffect(key1 = Unit) {
        loginViewModel.loginEvent.collect {
            when (it) {
                is LoginEvent.MoveToMainActivity -> {}
                is LoginEvent.NotFound -> {}
                is LoginEvent.UnAuthorization -> {}
            }
        }
    }
}