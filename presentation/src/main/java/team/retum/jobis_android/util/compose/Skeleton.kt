package team.retum.jobis_android.util.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import team.retum.jobisui.colors.JobisColor

@SuppressLint("ComposableModifierFactory")
@Composable
internal fun Modifier.skeleton(
    minWidth: Dp? = null,
    minHeight: Dp? = null,
    show: Boolean,
    shape: Shape = RoundedCornerShape(6.dp),
) = this
    .then(
        if (minWidth != null && minHeight != null) {
            Modifier.defaultMinSize(
                minWidth = minWidth,
                minHeight = minHeight,
            )
        } else Modifier
    )
    .clip(shape = shape)
    .then(
        if (show) Modifier.shimmer()
        else Modifier
    )
    .background(
        color = if (show) JobisColor.Gray500
        else Color.Transparent,
        shape = shape,
    )