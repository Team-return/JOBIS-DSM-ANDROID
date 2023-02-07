package com.jobis.design_system.button.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jobis.design_system.button.basic.direction.DRAWABLE_CENTER
import com.jobis.design_system.button.basic.direction.DRAWABLE_END
import com.jobis.design_system.button.basic.direction.DRAWABLE_START
import com.jobis.design_system.typography.typography

@Composable
fun BasicButton(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
    drawable: Int,
    direction: Int,
    backgroundColor: Color,
    outLineColor: Color,
    backgroundPressedColor: Color,
    outLinePressedColor: Color,
    textColor: Color,
    textPressedColor: Color,
    textStyle: TextStyle = typography.heading6,
    shape: Shape,
    disable: Boolean,
    shadow: Int,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonSolidColor = if (isPressed) backgroundPressedColor else backgroundColor
    val buttonOutLineColor = if (isPressed) outLinePressedColor else outLineColor
    val textColor = if(isPressed) textPressedColor else textColor

    Box(
        modifier = modifier
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
                enabled = !disable
            ).shadow(
                elevation = shadow.dp,
                shape = shape,
                clip = true,
            )
            .background(
                color = buttonSolidColor,
                shape = shape
            )
            .border(
                width = 1.5.dp,
                color = buttonOutLineColor,
                shape = shape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (direction == DRAWABLE_START) {
                Image(
                    painter = painterResource(id = drawable),
                    modifier = Modifier.size(
                        width = 24.dp,
                        height = 24.dp,
                    ),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    color = textColor,
                    style = textStyle,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            } else if (direction == DRAWABLE_END) {
                Text(
                    text = text,
                    color = textColor,
                    style = textStyle,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = drawable),
                    modifier = Modifier.size(
                        width = 24.dp,
                        height = 24.dp,
                    ),
                    contentDescription = null,
                )
            } else if (direction == DRAWABLE_CENTER) {
                Image(
                    painter = painterResource(id = drawable),
                    modifier = Modifier.size(
                        width = 24.dp,
                        height = 24.dp,
                    ),
                    contentDescription = null,
                )
            } else {
                Text(
                    text = text,
                    color = textColor,
                    style = textStyle,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}