package com.jobis.design_system.button.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jobis.design_system.color.color
import com.jobis.design_system.typography.typography

@Composable
fun BasicButton(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
    backgroundColor: Color,
    outLineColor: Color,
    backgroundPressedColor: Color,
    outLinePressedColor: Color,
    textColor: Color,
    textStyle: TextStyle = typography.heading6,
    shape: Shape,
    disable: Boolean,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonSolidColor = if(isPressed) backgroundPressedColor else backgroundColor
    val buttonOutLineColor = if(isPressed) outLinePressedColor else outLineColor

    Box(
        modifier = modifier.clickable(
            onClick = onClick,
            interactionSource = interactionSource,
            indication = null,
            enabled = !disable
        ).background(
            color = buttonSolidColor,
            shape = shape
        ).border(
            width = 1.5.dp,
            color = buttonOutLineColor,
            shape = shape,
        ),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = text,
            color = textColor,
            style = textStyle,
        )
    }
}

@Composable
fun DrawableButton(){

}

@Composable
fun StartDrawableButton(){

}

@Composable
fun EndDrawableButton(){

}

@Composable
fun SmallButton(
    onClick: () -> Unit,
    text: String,
    textColor: Color = color.Gray100,
    backgroundColor: Color = color.LightBlue,
    outLineColor: Color = backgroundColor,
    backgroundPressedColor: Color = color.LightBlue,
    outLinePressedColor: Color = backgroundPressedColor,
    disable: Boolean = false,
){
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
        disable = disable
    )
}

@Composable
fun MediumButton(
    onClick: () -> Unit,
    text: String,
    textColor: Color = color.Gray100,
    backgroundColor: Color = color.LightBlue,
    outLineColor: Color = backgroundColor,
    backgroundPressedColor: Color = color.LightBlue,
    outLinePressedColor: Color = backgroundPressedColor,
    disable: Boolean = false,
){
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
        disable = disable
    )
}

@Composable
fun LargeButton(
    onClick: () -> Unit,
    text: String,
    textColor: Color = color.Gray100,
    backgroundColor: Color = color.LightBlue,
    outLineColor: Color = backgroundColor,
    backgroundPressedColor: Color = color.LightBlue,
    outLinePressedColor: Color = backgroundPressedColor,
    disable: Boolean = false,
){
    BasicButton(
        modifier = Modifier.size(
            width =320.dp,
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
        disable = disable
    )
}
