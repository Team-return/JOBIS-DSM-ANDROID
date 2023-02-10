package com.jobis.jobis_android.feature.auth.login

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.contract.LoginSideEffect
import com.jobis.jobis_android.util.CollectWithLifecycle
import com.jobis.jobis_android.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = hiltViewModel()
) {

    val container = vm.container
    val sideEffectFlow = container.sideEffectFlow

    var isError by remember { mutableStateOf(false) }

    CollectWithLifecycle {
        sideEffectFlow.collect {
            // TODO show error message
            when (it) {
                is LoginSideEffect.Success -> { isError = true}
                is LoginSideEffect.UnAuthorization -> { isError = true}
                is LoginSideEffect.NotFound -> { isError = true }
            }
        }
    }
}
