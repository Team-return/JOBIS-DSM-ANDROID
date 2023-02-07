package com.jobis.design_system.button.basic

import androidx.compose.ui.graphics.Color
import com.jobis.design_system.color.color


data class BasicButtonColor(
    val backgroundColor: Color,
    val outLineColor: Color,
    val textColor: Color,
    val pressedColor: BasicButtonColor? = null,
    val disabledColor: BasicButtonColor? = null,
)

object ButtonColor {
    val SolidDefaultButtonColor = BasicButtonColor(
        backgroundColor = color.LightBlue,
        outLineColor = color.LightBlue,
        textColor = color.Gray100,
        pressedColor = PressedColor.SolidDefaultPressedColor,
        disabledColor = DisabledColor.SolidDisableButtonColor,
    )

    val GhostDefaultButtonColor = BasicButtonColor(
        backgroundColor = color.Gray100,
        outLineColor = color.LightBlue,
        textColor = color.LightBlue,
        pressedColor = PressedColor.GhostDefaultPressedColor,
        disabledColor = DisabledColor.GhostDisableButtonColor,
    )

    val LightDefaultButtonColor = BasicButtonColor(
        backgroundColor = color.Gray300,
        outLineColor = color.Gray300,
        textColor = color.LightBlue,
        pressedColor = PressedColor.LightDefaultPressedColor,
        disabledColor = DisabledColor.LightDisableButtonColor,
    )

    val GrayDefaultButtonColor = BasicButtonColor(
        backgroundColor = color.Gray300,
        outLineColor = color.Gray300,
        textColor = color.Gray900,
        pressedColor = PressedColor.GrayDefaultPressedColor,
        disabledColor = DisabledColor.LightDisableButtonColor,
    )

    val ShadowDefaultButtonColor = BasicButtonColor(
        backgroundColor = color.Gray100,
        outLineColor = color.Gray100,
        textColor = color.Gray900,
        pressedColor = PressedColor.ShadowDefaultPressedColor,
        disabledColor = DisabledColor.LightDisableButtonColor,
    )
}

object PressedColor {
    val SolidDefaultPressedColor = BasicButtonColor(
        backgroundColor = color.DarkBlue,
        outLineColor = color.DarkBlue,
        textColor = color.Gray100,
    )

    val GhostDefaultPressedColor = BasicButtonColor(
        backgroundColor = color.LightBlue,
        outLineColor = color.LightBlue,
        textColor = color.Gray100,
    )

    val LightDefaultPressedColor = BasicButtonColor(
        backgroundColor = color.LightBlue,
        outLineColor = color.LightBlue,
        textColor = color.Gray100,
    )

    val GrayDefaultPressedColor = BasicButtonColor(
        backgroundColor = color.Gray600,
        outLineColor = color.Gray600,
        textColor = color.Gray100,
    )

    val ShadowDefaultPressedColor = BasicButtonColor(
        backgroundColor = color.LightBlue,
        outLineColor = color.LightBlue,
        textColor = color.Gray100,
    )
}

object DisabledColor {
    val SolidDisableButtonColor = BasicButtonColor(
        backgroundColor = color.Gray500,
        outLineColor = color.Gray500,
        textColor = color.Gray100,
    )

    val GhostDisableButtonColor = BasicButtonColor(
        backgroundColor = color.Gray100,
        outLineColor = color.Gray500,
        textColor = color.Gray500,
    )

    val LightDisableButtonColor = BasicButtonColor(
        backgroundColor = color.Gray100,
        outLineColor = color.Gray500,
        textColor = color.Gray500,
    )
}


