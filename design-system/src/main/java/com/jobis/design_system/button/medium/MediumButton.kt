package com.jobis.design_system.button.medium

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jobis.design_system.button.basic.BasicOutLineButton
import com.jobis.design_system.button.basic.BasicSolidButton
import com.jobis.design_system.typography.typography

@Composable
fun MediumSolidButton(
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color,
    text: String,
    disable: Boolean,
) {
    BasicSolidButton(
        onClick = onClick,
        text = text,
        width = 132,
        height = 44,
        roundSize = 8,
        textStyle = typography.heading6,
        backgroundColor = backgroundColor,
        textColor = textColor,
        disable = disable,
    )
}

@Composable
fun MediumOutLineButton(
    onClick: () -> Unit,
    textColor: Color,
    text: String,
    disable: Boolean,
) {
    BasicOutLineButton(
        onClick = onClick,
        text = text,
        width = 132,
        height = 44,
        roundSize = 8,
        textStyle = typography.heading6,
        textColor = textColor,
        disable = disable,
    )
}