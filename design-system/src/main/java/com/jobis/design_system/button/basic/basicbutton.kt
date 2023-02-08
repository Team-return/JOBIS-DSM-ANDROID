package com.jobis.design_system.button.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jobis.design_system.color.BasicButtonColor
import com.jobis.design_system.util.Direction.DRAWABLE_CENTER
import com.jobis.design_system.util.Direction.DRAWABLE_END
import com.jobis.design_system.util.Direction.DRAWABLE_START
import com.jobis.design_system.typography.typography
import com.jobis.design_system.util.DrawableSize
import com.jobis.design_system.util.MediumWidthSpacer
import com.jobis.design_system.util.click

@Stable
val BorderWidth = 1.5.dp

@Composable
fun BasicButton(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
    drawable: Int,
    direction: Int,
    color: BasicButtonColor,
    textStyle: TextStyle = typography.heading6,
    shape: Shape,
    shadowWidth: Dp,
    disable: Boolean,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor = if (isPressed) color.pressedColor!!.backgroundColor
    else if (disable) color.disabledColor!!.backgroundColor
    else color.backgroundColor

    val outLineColor = if (isPressed) color.pressedColor!!.outLineColor
    else if (disable) color.disabledColor!!.outLineColor
    else color.outLineColor

    val textColor = if (isPressed) color.pressedColor!!.textColor
    else if (disable) color.disabledColor!!.textColor
    else color.textColor

    Box(
        modifier = modifier
            .click(
                onClick = onClick,
                interactionSource = interactionSource,
                disable = disable,
            )
            .shadow(
                elevation = shadowWidth,
                shape = shape,
            )
            .background(
                color = backgroundColor,
                shape = shape,
            )
            .border(
                width = BorderWidth,
                color = outLineColor,
                shape = shape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when(direction) {
                DRAWABLE_START -> {
                    Image(
                        painter = painterResource(id = drawable),
                        modifier = DrawableSize,
                        contentDescription = null,
                    )
                    Spacer(modifier = MediumWidthSpacer)
                    Text(
                        text = text,
                        color = textColor,
                        style = textStyle,
                        modifier = Modifier.padding(bottom = 2.dp),
                    )
                }
                DRAWABLE_END -> {
                    Text(
                        text = text,
                        color = textColor,
                        style = textStyle,
                        modifier = Modifier.padding(bottom = 2.dp),
                    )
                    Spacer(modifier = MediumWidthSpacer)
                    Image(
                        painter = painterResource(id = drawable),
                        modifier = DrawableSize,
                        contentDescription = null,
                    )
                }
                DRAWABLE_CENTER -> {
                    Image(
                        painter = painterResource(id = drawable),
                        modifier = DrawableSize,
                        contentDescription = null,
                    )
                }
                else -> {
                    Text(
                        text = text,
                        color = textColor,
                        style = textStyle,
                        modifier = Modifier.padding(bottom = 2.dp),
                    )
                }
            }
        }
    }
}