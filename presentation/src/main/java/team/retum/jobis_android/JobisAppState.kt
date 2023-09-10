package team.retum.jobis_android

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import team.returm.jobisdesignsystem.toast.ToastType

@Stable
internal val LocalAppState = staticCompositionLocalOf<JobisAppState> {
    error("Not implemented")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    snackbarHostState: SnackbarHostState = scaffoldState.snackbarHostState,
    navController: NavHostController = rememberAnimatedNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember {
    JobisAppState(
        scaffoldState = scaffoldState,
        snackbarHostState = snackbarHostState,
        navController = navController,
        coroutineScope = coroutineScope,
    )
}

@Stable
internal class JobisAppState(
    val scaffoldState: ScaffoldState,
    val snackbarHostState: SnackbarHostState,
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
) {

    private val _toastState = MutableStateFlow(ToastState())
    val toastState = _toastState.asStateFlow()

    fun dismissToast() {
        snackbarHostState.currentSnackbarData?.dismiss()
    }

    private fun showToast(
        message: String,
        title: String? = null,
        toastType: ToastType,
    ) {
        coroutineScope.launch {
            _toastState.emit(
                ToastState(
                    title = title,
                    toastType = toastType,
                )
            )
            snackbarHostState.showSnackbar(
                message = message,
            )
        }
    }

    internal fun showSuccessToast(
        message: String,
        title: String? = null,
    ) {
        showToast(
            message = message,
            title = title,
            toastType = ToastType.Success,
        )
    }

    internal fun showErrorToast(
        message: String,
        title: String? = null,
    ) {
        showToast(
            message = message,
            title = title,
            toastType = ToastType.Error,
        )
    }

    internal fun showNormalToast(
        message: String,
        title: String? = null,
    ) {
        showToast(
            message = message,
            title = title,
            toastType = ToastType.Normal,
        )
    }

    internal fun showWarningToast(
        message: String,
        title: String? = null,
    ) {
        showToast(
            message = message,
            title = title,
            toastType = ToastType.Warning,
        )
    }
}

internal data class ToastState(
    val title: String? = null,
    val toastType: ToastType = ToastType.Normal,
)