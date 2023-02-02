package com.jobis.design_system.button.small

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jobis.design_system.button.basic.BasicOutLineButton
import com.jobis.design_system.button.basic.BasicSolidButton
import com.jobis.design_system.typography.typography

@Composable
fun SmallSolidButton(
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color,
    text: String,
    disable: Boolean,
) {
    BasicSolidButton(
        onClick = onClick,
        text = text,
        width = 72,
        height = 32,
        roundSize = 16,
        textStyle = typography.body2,
        backgroundColor = backgroundColor,
        textColor = textColor,
        disable = disable,
    )
}

@Composable
fun SmallOutLineButton(
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color,
    text: String,
    disable: Boolean,
) {
    BasicOutLineButton(
        onClick = onClick,
        text = text,
        width = 72,
        height = 32,
        roundSize = 16,
        textStyle = typography.body2,
        backgroundColor = backgroundColor,
        textColor = textColor,
        disable = disable,
    )
}