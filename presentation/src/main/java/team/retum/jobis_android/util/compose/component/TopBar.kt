package team.retum.jobis_android.util.compose.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.theme.Body1
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
fun TopBar(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .jobisClickable(onClick = onClick),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JobisImage(
            modifier = Modifier.padding(top = 2.dp),
            drawable = JobisIcon.LeftArrow,
            onClick = onClick,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Body1(
            text = text,
            color = JobisColor.Gray700,
        )
    }
}