package team.retum.design_system.button.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.jobis.design_system.button.basic.BasicButton
import com.jobis.design_system.button.size.*
import com.jobis.design_system.color.BasicButtonColor
import com.jobis.design_system.color.ButtonColor
import com.jobis.design_system.typography.typography
import com.jobis.design_system.util.Direction.DRAWABLE_CENTER
import com.jobis.design_system.util.Direction.DRAWABLE_END
import com.jobis.design_system.util.Direction.DRAWABLE_START
import com.jobis.design_system.util.LargeShape
import com.jobis.design_system.util.MediumShape

@Stable
val ShadowWidth = 4.dp

@Composable
fun SmallButton(
    onClick: () -> Unit,
    text: String = "",
    drawable: Int = 0,
    direction: Int = 0,
    color: BasicButtonColor,
    shadowWidth: Dp = 0.dp,
    disable: Boolean = false,
) {

    val center = direction == DRAWABLE_CENTER

    val shape = if (center) CircleShape
    else LargeShape

    val modifier = if (center) SmallCircleSize
    else SmallButtonSize

    BasicButton(
        onClick = onClick,
        modifier = modifier,
        text = text,
        drawable = drawable,
        direction = direction,
        color = color,
        textStyle = typography.body2,
        shape = shape,
        shadowWidth = shadowWidth,
        disable = disable,
    )
}

@Composable
fun MediumButton(
    onClick: () -> Unit,
    text: String = "",
    drawable: Int = 0,
    direction: Int = 0,
    color: BasicButtonColor,
    shape: Shape = MediumShape,
    shadowWidth: Dp = 0.dp,
    disable: Boolean = false,
) {

    val center = direction == DRAWABLE_CENTER

    val modifier = if (center) MediumCircleSize
    else MediumButtonSize

    BasicButton(
        onClick = onClick,
        modifier = modifier,
        text = text,
        drawable = drawable,
        direction = direction,
        color = color,
        textStyle = typography.body2,
        shape = shape,
        shadowWidth = shadowWidth,
        disable = disable,
    )
}

@Composable
fun LargeButton(
    onClick: () -> Unit,
    text: String = "",
    drawable: Int = 0,
    direction: Int = 0,
    color: BasicButtonColor,
    shadowWidth: Dp = 0.dp,
    disable: Boolean = false,
) {

    val center = direction == DRAWABLE_CENTER

    val shape = if (center) CircleShape
    else MediumShape

    BasicButton(
        onClick = onClick,
        modifier = LargeButtonSize,
        text = text,
        drawable = drawable,
        direction = direction,
        color = color,
        textStyle = typography.heading6,
        shape = shape,
        shadowWidth = shadowWidth,
        disable = disable,
    )
}

@Composable
@Preview(
    name = "SmallButtonPreview",
    showBackground = true,
)
fun SmallButtonPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.SolidDefaultButtonColor,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.SolidDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.SolidDefaultButtonColor,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.SolidDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GhostDefaultButtonColor,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GhostDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.GhostDefaultButtonColor,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.GhostDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.LightDefaultButtonColor,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.LightDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.LightDefaultButtonColor,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.LightDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GrayDefaultButtonColor,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GrayDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.ShadowDefaultButtonColor,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.ShadowDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.ShadowDefaultButtonColor,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.ShadowDefaultButtonColor,
                disable = true,
            )
        }
    }
}

@Composable
@Preview(
    name = "MediumButtonPreview",
    showBackground = true,
    heightDp = 1200
)
fun MediumButtonPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.SolidDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.SolidDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.SolidDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.SolidDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.SolidDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.SolidDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.SolidDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.SolidDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.SolidDefaultButtonColor,
                shape = CircleShape,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.SolidDefaultButtonColor,
                shape = CircleShape,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GhostDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GhostDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.GhostDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.GhostDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.GhostDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.GhostDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.GhostDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.GhostDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.GhostDefaultButtonColor,
                shape = CircleShape,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.GhostDefaultButtonColor,
                shape = CircleShape,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.LightDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.LightDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.LightDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.LightDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.LightDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.LightDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.LightDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.LightDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.LightDefaultButtonColor,
                shape = CircleShape,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.LightDefaultButtonColor,
                shape = CircleShape,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GrayDefaultButtonColor,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GrayDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
                shape = CircleShape,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
                shape = CircleShape,
                disable = true,
            )
        }
    }
}

@Composable
@Preview(
    name = "LargeButtonPreview",
    showBackground = true,
    widthDp = 800,
)
fun LargeButtonPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.SolidDefaultButtonColor,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.SolidDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.SolidDefaultButtonColor,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.SolidDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.SolidDefaultButtonColor,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.SolidDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GhostDefaultButtonColor,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GhostDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.GhostDefaultButtonColor,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.GhostDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.GhostDefaultButtonColor,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.GhostDefaultButtonColor,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.LightDefaultButtonColor,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.LightDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.LightDefaultButtonColor,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.LightDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.LightDefaultButtonColor,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.LightDefaultButtonColor,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GrayDefaultButtonColor,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.GrayDefaultButtonColor,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                color = ButtonColor.ShadowDefaultButtonColor,
                shadowWidth = ShadowWidth,
                disable = true,
            )
        }
    }
}