package team.retum.jobis_android.feature.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jobis.jobis_android.R
import kotlinx.coroutines.delay
import team.retum.jobis_android.viewmodel.main.MainViewModel
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.util.Animated

@Composable 
internal fun SplashScreen(
    moveToScreenBySignInOption: () -> Unit,
    mainViewModel: MainViewModel,
) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(2000)
        mainViewModel.fetchAutoSignInOption()
        moveToScreenBySignInOption()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Animated(
            visible = visible,
            isBounce = true,
        ) {
            JobisImage(
                drawable = R.drawable.ic_splash,
            )
        }
    }
}