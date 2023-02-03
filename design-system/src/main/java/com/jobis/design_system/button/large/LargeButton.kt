package com.jobis.design_system.button.large

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jobis.design_system.button.basic.BasicOutLineButton
import com.jobis.design_system.button.basic.BasicSolidButton
import com.jobis.design_system.typography.typography

@Composable
fun LargeSolidButton(
    onClick: ()->Unit,
    backgroundColor: Color,
    textColor: Color,
    text: String,
    disable: Boolean,
){
    BasicSolidButton(
        onClick = onClick,
        text = text,
        width = 320,
        height = 48,
        roundSize = 12,
        textStyle = typography.heading6,
        backgroundColor = backgroundColor,
        textColor = textColor,
        disable = disable,
    )
}

@Composable
fun LargeOutLineButton(
    onClick: ()->Unit,
    textColor: Color,
    text: String,
    disable: Boolean,
){
    BasicOutLineButton(
        onClick = onClick,
        text = text,
        width = 320,
        height = 48,
        roundSize = 12,
        textStyle = typography.heading6,
        textColor = textColor,
        disable = disable,
    )
}