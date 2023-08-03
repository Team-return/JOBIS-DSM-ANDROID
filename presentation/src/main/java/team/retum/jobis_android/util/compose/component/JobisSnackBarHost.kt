package team.retum.jobis_android.util.compose.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import team.retum.jobis_android.root.JobisAppState
import team.retum.jobis_android.root.JobisBasicToast
import team.retum.jobis_android.root.ToastState

@Composable
internal fun JobisSnackBarHost(
    appState: JobisAppState,
) {

    val coroutineScope = rememberCoroutineScope()

    var toastState = ToastState()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            appState.toastState.collect {
                toastState = it
            }
        }
    }

    val dismissToToast = {
        appState.dismissToast()
    }

    SnackbarHost(
        modifier = Modifier.fillMaxSize(),
        hostState = appState.snackbarHostState,
    ) {
        ToastLayout {
            JobisBasicToast(
                message = it.message,
                title = toastState.title,
                toastType = toastState.toastType,
                dismissToToast = dismissToToast,
            )
        }
    }
}