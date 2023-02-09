package com.jobis.jobis_android.feature.auth.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val state = container.stateFlow.collectAsStateWithLifecycle().value
    val sideEffectFlow = container.sideEffectFlow

    CollectWithLifecycle {
        sideEffectFlow.collect {
            when (it) {
                is LoginSideEffect.Success -> {}
                is LoginSideEffect.UnAuthorization -> {}
                is LoginSideEffect.NotFound -> {}
            }
        }
    }
}
