package com.jobis.design_system.button.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.BuildCompat.PrereleaseSdkCheck
import androidx.leanback.widget.Row
import com.example.design_system.R
import com.jobis.design_system.button.basic.BasicButton
import com.jobis.design_system.button.basic.direction
import com.jobis.design_system.button.basic.direction.DRAWABLE_CENTER
import com.jobis.design_system.color.color
import com.jobis.design_system.typography.typography

@Composable
fun SmallButton(
    onClick: () -> Unit,
    text: String = "",
    drawable: Int = 0,
    direction: Int = 0,
    textColor: Color = color.Gray100,
    textPressedColor: Color = color.Gray100,
    backgroundColor: Color = color.LightBlue,
    outLineColor: Color = backgroundColor,
    backgroundPressedColor: Color = color.LightBlue,
    outLinePressedColor: Color = backgroundPressedColor,
    disable: Boolean = false,
    shadow: Int = 0
) {
    BasicButton(
        modifier = if (direction == DRAWABLE_CENTER)
            Modifier.size(width = 32.dp, height = 32.dp)
        else Modifier.size(width = 72.dp, height = 32.dp),
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
        textPressedColor = textPressedColor,
        shadow = shadow
    )
}

@Composable
fun MediumButton(
    onClick: () -> Unit,
    text: String = "",
    drawable: Int = 0,
    direction: Int = 0,
    textColor: Color = color.Gray100,
    textPressedColor: Color = color.Gray100,
    backgroundColor: Color = color.LightBlue,
    outLineColor: Color = backgroundColor,
    backgroundPressedColor: Color = color.LightBlue,
    outLinePressedColor: Color = backgroundPressedColor,
    disable: Boolean = false,
    shadow: Int = 0,
) {
    BasicButton(
        modifier = if (direction == DRAWABLE_CENTER)
            Modifier.size(width = 44.dp, height = 44.dp)
        else Modifier.size(width = 72.dp, height = 32.dp),
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
        textPressedColor = textPressedColor,
        shadow = shadow,
    )
}

@Composable
fun LargeButton(
    onClick: () -> Unit,
    text: String = "",
    drawable: Int = 0,
    direction: Int = 0,
    textColor: Color = color.Gray100,
    textPressedColor: Color = color.Gray100,
    backgroundColor: Color = color.LightBlue,
    outLineColor: Color = backgroundColor,
    backgroundPressedColor: Color = color.LightBlue,
    outLinePressedColor: Color = backgroundPressedColor,
    disable: Boolean = false,
    shadow: Int = 0,
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
        textPressedColor = textPressedColor,
        shadow = shadow,
    )
}

@Composable
@Preview(
    showSystemUi = true,
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
                backgroundPressedColor = color.DarkBlue,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                backgroundColor = color.Gray500,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                drawable = R.drawable.ic_temp,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray500,
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
                textColor = color.LightBlue,
                backgroundColor = color.Gray100,
                outLineColor = color.LightBlue,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray500,
                backgroundColor = color.Gray100,
                outLineColor = color.Gray500,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                outLineColor = color.LightBlue,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray100,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                drawable = R.drawable.ic_temp,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
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
                textColor = color.LightBlue,
                backgroundColor = color.Gray300,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray500,
                backgroundColor = color.Gray300,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray100,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray300,
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
                textColor = color.Gray900,
                backgroundColor = color.Gray300,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray500,
                backgroundColor = color.Gray300,
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
                textColor = color.Gray900,
                backgroundColor = color.Gray100,
                shadow = 4,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray500,
                backgroundColor = color.Gray300,
                disable = true,
                shadow = 4
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            SmallButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray100,
                shadow = 4,
            )
            SmallButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray300,
                disable = true,
                shadow = 4,
            )
        }
    }
}