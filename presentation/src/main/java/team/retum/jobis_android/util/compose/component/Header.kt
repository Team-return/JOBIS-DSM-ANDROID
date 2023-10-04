package team.retum.jobis_android.util.compose.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun Header(
    text: String,
    @DrawableRes drawableRes: Int? = null,
    onClick: (() -> Unit)? = null,
) {
    Column {
        Row {
            Body2(
                text = text,
                color = JobisColor.Gray600,
            )
            if (drawableRes != null) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier.jobisClickable(onClick = onClick ?: {}),
                    painter = painterResource(id = drawableRes),
                    contentDescription = null,
                )
            }
        }
        Spacer(
            modifier = Modifier.height(10.dp),
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = JobisColor.Gray400,
        )
    }
}