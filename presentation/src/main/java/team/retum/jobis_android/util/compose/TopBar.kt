package team.retum.jobis_android.util.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.ui.theme.Body1
import team.retum.jobisui.util.jobisClickable
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.image.JobisImage

@Composable
fun TopBar(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .jobisClickable(
                rippleEnabled = false,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JobisImage(
            modifier = Modifier.padding(top = 2.dp),
            drawable = JobisIcon.LeftArrow,
        ) {
            onClick()
        }
        Spacer(modifier = Modifier.width(4.dp))
        Body1(
            text = text,
            color = JobisColor.Gray700,
        )
    }
}