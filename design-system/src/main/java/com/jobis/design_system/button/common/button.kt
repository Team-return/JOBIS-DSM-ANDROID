package com.jobis.design_system.button.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jobis.design_system.button.basic.BasicButton
import com.jobis.design_system.color.color
import com.jobis.design_system.typography.typography

@Composable
fun SmallButton(
    onClick: () -> Unit,
    text: String = "",
    drawable: Int = 0,
    direction: Int = 0,
    textColor: Color = color.Gray100,
    backgroundColor: Color = color.LightBlue,
    outLineColor: Color = backgroundColor,
    backgroundPressedColor: Color = color.LightBlue,
    outLinePressedColor: Color = backgroundPressedColor,
    disable: Boolean = false,
) {
    BasicButton(
        modifier = Modifier.size(
            width = 72.dp,
            height = 32.dp,
        ),
        outLineColor = outLineColor,
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        text = text,
        textColor = textColor,
        onClick = onClick,
        backgroundPressedColor = backgroundPressedColor,
        outLinePressedColor = outLinePressedColor,
        disable = disable,
        drawable = drawable,
        direction = direction,
        textStyle = typography.body2,
    )
}

@Composable
fun MediumButton(
    onClick: () -> Unit,
    text: String = "",
    drawable: Int = 0,
    direction: Int = 0,
    textColor: Color = color.Gray100,
    backgroundColor: Color = color.LightBlue,
    outLineColor: Color = backgroundColor,
    backgroundPressedColor: Color = color.LightBlue,
    outLinePressedColor: Color = backgroundPressedColor,
    disable: Boolean = false,
) {
    BasicButton(
        modifier = Modifier.size(
            width = 132.dp,
            height = 44.dp,
        ),
        outLineColor = outLineColor,
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        text = text,
        textColor = textColor,
        onClick = onClick,
        backgroundPressedColor = backgroundPressedColor,
        outLinePressedColor = outLinePressedColor,
        disable = disable,
        drawable = drawable,
        direction = direction,
    )
}

@Composable
fun LargeButton(
    onClick: () -> Unit,
    text: String = "",
    drawable: Int = 0,
    direction: Int = 0,
    textColor: Color = color.Gray100,
    backgroundColor: Color = color.LightBlue,
    outLineColor: Color = backgroundColor,
    backgroundPressedColor: Color = color.LightBlue,
    outLinePressedColor: Color = backgroundPressedColor,
    disable: Boolean = false,
) {
    BasicButton(
        modifier = Modifier.size(
            width = 320.dp,
            height = 50.dp,
        ),
        outLineColor = outLineColor,
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        text = text,
        textColor = textColor,
        onClick = onClick,
        backgroundPressedColor = backgroundPressedColor,
        outLinePressedColor = outLinePressedColor,
        disable = disable,
        drawable = drawable,
        direction = direction,
    )
}