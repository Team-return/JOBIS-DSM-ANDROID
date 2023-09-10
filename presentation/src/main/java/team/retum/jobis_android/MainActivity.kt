package team.retum.jobis_android

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobis.jobis_android.R
import dagger.hilt.android.AndroidEntryPoint
import team.retum.jobis_android.util.compose.component.JobisSnackBarHost
import team.retum.jobis_android.viewmodel.main.MainViewModel
import team.retum.jobis_android.viewmodel.resetpassword.ResetPasswordViewModel
import team.retum.jobis_android.viewmodel.signup.SignUpViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private val signUpViewModel by viewModels<SignUpViewModel>()
    private val resetPasswordViewModel by viewModels<ResetPasswordViewModel>()

    private var delay = 0L

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContent {
            BackHandler {
                setBackHandler()
            }

            val appState = rememberAppState()

            val signInOption =
                mainViewModel.container.stateFlow.collectAsStateWithLifecycle().value.autoSignInOption

            CompositionLocalProvider(LocalAppState provides appState) {
                Scaffold(
                    scaffoldState = appState.scaffoldState,
                    snackbarHost = { JobisSnackBarHost(appState) },
                ) {
                    JobisApp(
                        signInOption = signInOption,
                        signUpViewModel = signUpViewModel,
                        resetPasswordViewModel = resetPasswordViewModel,
                    )
                }
            }
        }
    }

    private fun setBackHandler() {
        if (System.currentTimeMillis() > delay) {
            delay = System.currentTimeMillis() + 1000
            Toast.makeText(this, getText(R.string.back_handler), Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }
}