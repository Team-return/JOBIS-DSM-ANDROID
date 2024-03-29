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
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.jobis.jobis_android.R
import dagger.hilt.android.AndroidEntryPoint
import org.orbitmvi.orbit.compose.collectAsState
import team.retum.jobis_android.feature.main.MainViewModel
import team.retum.jobis_android.util.compose.component.JobisSnackBarHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    private var delay = 0L

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAppUpdate()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContent {
            val appState = rememberAppState()
            val state by mainViewModel.collectAsState()

            BackHandler {
                setBackHandler()
            }

            CompositionLocalProvider(LocalAppState provides appState) {
                Scaffold(
                    scaffoldState = appState.scaffoldState,
                    snackbarHost = { JobisSnackBarHost(appState) },
                ) {
                    JobisApp(signInOption = state.autoSignInOption)
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

    private fun checkAppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener {
            val isUpdateAvailable = it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE

            if (isUpdateAvailable && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                appUpdateManager.startUpdateFlowForResult(
                    it,
                    AppUpdateType.IMMEDIATE,
                    this,
                    0,
                )
            }
        }
    }
}
