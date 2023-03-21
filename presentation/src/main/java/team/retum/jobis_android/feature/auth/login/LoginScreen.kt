package team.retum.jobis_android.feature.auth.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.retum.jobis_android.viewmodel.login.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = hiltViewModel()
) {

    val container = vm.container
    val sideEffectFlow = container.sideEffectFlow
}
