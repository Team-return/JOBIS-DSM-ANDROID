package team.retum.jobis_android.util.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisSmallIconButton

@Composable
internal fun AddButton(
    @DrawableRes drawable: Int,
    onClick: () -> Unit,
) {
    JobisSmallIconButton(
        drawable = drawable,
        onClick = {
            onClick()
        },
        color = JobisButtonColor.MainShadowColor,
        shadow = true,
    )
}