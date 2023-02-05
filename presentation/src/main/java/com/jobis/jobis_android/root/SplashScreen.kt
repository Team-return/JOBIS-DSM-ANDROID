package com.jobis.jobis_android.feature

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.JobisScreen
import com.jobis.jobis_android.R
import kotlinx.coroutines.delay

@Preview(
    showBackground = true,
)
@Composable
fun SplashPreview() {

}

@Composable
fun SplashScreen(
    navController: NavController,
) {
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


    LaunchedEffect(
        key1 = Unit,
    ) {
        delay(3000)
        navController.navigate(
            route = JobisScreen.LOGIN.route,
        )
    }
}