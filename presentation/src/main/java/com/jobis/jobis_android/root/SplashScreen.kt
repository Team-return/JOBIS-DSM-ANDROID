package com.jobis.jobis_android.root

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import com.jobis.jobis_android.contract.LoginSideEffect
import com.jobis.jobis_android.root.navigation.JobisRoute
import com.jobis.jobis_android.util.CollectWithLifecycle
import com.jobis.jobis_android.viewmodel.splash.SplashViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {

    splashViewModel.postLogin()

    val sideEffectFlow = splashViewModel.container.sideEffectFlow

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App logo",
                modifier = Modifier.padding(64.dp)
            )
        }
    }

    CollectWithLifecycle {
        sideEffectFlow.collect {
            when (it) {
                is LoginSideEffect.Success -> {
                    navController.navigate(route = JobisRoute.HOME.route)
                }
                else -> {
                    navController.navigate(route = JobisRoute.Auth.route)
                }
            }
        }
    }
}