package com.jobis.design_system.color

import androidx.compose.ui.graphics.Color

data class BasicBoxTextFieldColor(
    val outLineColor: Color,
    val fieldTextColor: Color,
    val helperTextColor: Color? = null,
    val textColor: Color,
    val hintColor: Color? = null,
    val backgroundColor: Color,
    val errorColor: Color? = null,
    val focusedColor: Color? = null,
    val disabledColor: BasicBoxTextFieldColor? = null,
)

data class BasicUnderLineTextFieldColor(
    val underLineColor: Color,
    val fieldTextColor: Color,
    val helperTextColor: Color? = null,
    val textColor: Color,
    val hintColor: Color? = null,
    val errorColor: Color? = null,
    val focusedColor: Color? = null,
    val disabledColor: BasicUnderLineTextFieldColor? = null,
)

object BoxTextFieldColor{
    val DefaultBoxTextFieldColor = BasicBoxTextFieldColor(
        outLineColor = color.Gray400,
        fieldTextColor = color.Gray700,
        helperTextColor = color.Gray600,
        textColor = color.Gray900,
        hintColor = color.Gray600,
        backgroundColor = color.Gray100,
        errorColor = color.Red,
        focusedColor = color.Gray600,
        disabledColor = BoxDisabledTextFieldColor.DefaultBoxDisabledTextFieldColor,
    )
}

object UnderLineTextFieldColor{
    val DefaultUnderLineTextFieldColor = BasicUnderLineTextFieldColor(
        underLineColor = color.Gray400,
        fieldTextColor = color.Gray700,
        helperTextColor = color.Gray600,
        textColor = color.Gray900,
        hintColor = color.Gray600,
        errorColor = color.Red,
        focusedColor = color.LightBlue,
        disabledColor = UnderLineDisabledTextFieldColor.DefaultUnderLineDisabledTextFieldColor,
    )
}

object BoxDisabledTextFieldColor{
    val DefaultBoxDisabledTextFieldColor = BasicBoxTextFieldColor(
        outLineColor = color.Gray400,
        fieldTextColor = color.Gray500,
        textColor = color.Gray500,
        backgroundColor = color.Gray300,
    )
}

object UnderLineDisabledTextFieldColor{
    val DefaultUnderLineDisabledTextFieldColor = BasicUnderLineTextFieldColor(
        underLineColor = color.Gray400,
        fieldTextColor = color.Gray500,
        textColor = color.Gray500,
    )
}