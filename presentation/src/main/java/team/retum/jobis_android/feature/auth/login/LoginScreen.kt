package team.retum.jobis_android.feature.auth.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.retum.jobis_android.contract.LoginSideEffect
import team.retum.jobis_android.root.navigation.JobisScreen
import team.retum.jobis_android.util.CollectWithLifecycle
import team.retum.jobis_android.viewmodel.login.LoginViewModel
import team.retum.jobisui.textfield.JobisBoxTextField

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
                is LoginSideEffect.MoveToMain -> {
                    navController.navigate(JobisScreen.Home.HOME)
                }
                is LoginSideEffect.BadRequest -> {}
                is LoginSideEffect.UnAuthorization -> {}
                is LoginSideEffect.NotFound -> {}
                is LoginSideEffect.OnServerError -> {}
                is LoginSideEffect.MoveToLogin -> {}
            }
        }
    }
}
