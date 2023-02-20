package com.jobis.jobis_android.feature.auth.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.contract.LoginSideEffect
import com.jobis.jobis_android.root.navigation.JobisRoute
import com.jobis.jobis_android.util.CollectWithLifecycle
import com.jobis.jobis_android.viewmodel.login.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = hiltViewModel()
) {

    val container = vm.container
    val sideEffectFlow = container.sideEffectFlow

    CollectWithLifecycle {
        sideEffectFlow.collect {
            // TODO show error message
            when (it) {
                is LoginSideEffect.Success -> {
                    navController.navigate(JobisRoute.HOME.route)
                }
                is LoginSideEffect.UnAuthorization -> {}
                is LoginSideEffect.NotFound -> {}
                is LoginSideEffect.OnServerError -> {}
            }
        }
    }
}
