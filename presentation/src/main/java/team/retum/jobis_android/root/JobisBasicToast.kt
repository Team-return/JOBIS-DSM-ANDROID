package team.retum.jobis_android.root

import androidx.compose.runtime.Composable
import team.returm.jobisdesignsystem.toast.JobisErrorToast
import team.returm.jobisdesignsystem.toast.JobisNormalToast
import team.returm.jobisdesignsystem.toast.JobisSuccessToast
import team.returm.jobisdesignsystem.toast.JobisWarningToast
import team.returm.jobisdesignsystem.toast.ToastType

@Composable
internal fun JobisBasicToast(
    message: String,
    title: String? = null,
    toastType: ToastType,
    dismissToToast: () -> Unit,
){
    when(toastType){
        ToastType.Normal -> JobisNormalToast(
            message = message,
            title = title,
            dismissToToast = dismissToToast,
        )

        ToastType.Success -> JobisSuccessToast(
            message = message,
            title = title,
            dismissToToast = dismissToToast,
        )

        ToastType.Error -> JobisErrorToast(
            message = message,
            title = title,
            dismissToToast = dismissToToast,
        )

        ToastType.Warning -> JobisWarningToast(
            message = message,
            title = title,
            dismissToToast = dismissToToast,
        )
    }
}