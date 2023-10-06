package team.retum.jobis_android.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jobis.jobis_android.R
import kotlinx.coroutines.delay
import team.returm.jobisdesignsystem.util.Animated

@Composable
internal fun SplashScreen(
    moveToScreenBySignInOption: () -> Unit,
) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(2000)
        moveToScreenBySignInOption()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp),
        contentAlignment = Alignment.Center,
    ) {
        Animated(
            visible = visible,
            isBounce = true,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash),
                contentDescription = stringResource(id = R.string.content_description_splash_image),
            )
        }
    }
}