package com.jobis.jobis_android.root

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import com.jobis.jobis_android.contract.LoginSideEffect
import com.jobis.jobis_android.root.navigation.JobisScreen
import com.jobis.jobis_android.util.CollectWithLifecycle
import com.jobis.jobis_android.viewmodel.splash.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    vm: SplashViewModel = hiltViewModel(),
) {

    val sideEffectFlow = vm.container.sideEffectFlow

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
                contentDescription = null,
                modifier = Modifier.padding(64.dp)
            )
        }
    }

    LaunchedEffect(key1 = Unit){
        vm.postLogin()
    }

    CollectWithLifecycle {
        sideEffectFlow.collect {
            delay(3000)
            when (it) {
                is LoginSideEffect.MoveToMain -> {
                    navController.navigate(route = JobisScreen.Home.HOME)
                }
                else -> {
                    navController.navigate(route = JobisScreen.Auth.LOGIN)
                }
            }
        }
    }
}