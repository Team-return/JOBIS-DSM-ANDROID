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
import com.example.design_system.R
import com.jobis.design_system.button.basic.BasicButton
import com.jobis.design_system.button.basic.direction.DRAWABLE_CENTER
import com.jobis.design_system.button.basic.direction.DRAWABLE_END
import com.jobis.design_system.button.basic.direction.DRAWABLE_START
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
    shape: Int = 8,
) {
    BasicButton(
        modifier = if (direction == DRAWABLE_CENTER)
            Modifier.size(width = 44.dp, height = 44.dp)
        else Modifier.size(width = 132.dp, height = 44.dp),
        outLineColor = outLineColor,
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(shape.dp),
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
            width = 320.dp,
            height = 50.dp,
        ),
        outLineColor = outLineColor,
        backgroundColor = backgroundColor,
        shape = RoundedCornerShape(8.dp),
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
    name = "SmallButtonPreview",
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
                backgroundPressedColor = color.Gray600,
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

@Composable
@Preview(
    name = "MediumButtonPreview",
    showBackground = true,
    heightDp = 1200
)
fun MediumButtonPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                backgroundPressedColor = color.DarkBlue,
            )
            MediumButton(
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
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                direction = DRAWABLE_START,
                drawable = R.drawable.ic_temp,
                backgroundPressedColor = color.DarkBlue,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                direction = DRAWABLE_START,
                backgroundColor = color.Gray500,
                drawable = R.drawable.ic_temp,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                direction = DRAWABLE_END,
                drawable = R.drawable.ic_temp,
                backgroundPressedColor = color.DarkBlue,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                direction = DRAWABLE_END,
                backgroundColor = color.Gray500,
                drawable = R.drawable.ic_temp,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                backgroundPressedColor = color.DarkBlue,
                drawable = R.drawable.ic_temp,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                backgroundColor = color.Gray500,
                drawable = R.drawable.ic_temp,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                backgroundPressedColor = color.DarkBlue,
                drawable = R.drawable.ic_temp,
                shape = 50,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                backgroundColor = color.Gray500,
                drawable = R.drawable.ic_temp,
                disable = true,
                shape = 50
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.LightBlue,
                backgroundColor = color.Gray100,
                outLineColor = color.LightBlue,
            )
            MediumButton(
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
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.LightBlue,
                direction = DRAWABLE_START,
                outLineColor = color.LightBlue,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.DarkBlue,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray500,
                direction = DRAWABLE_START,
                outLineColor = color.Gray500,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray100,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.LightBlue,
                direction = DRAWABLE_END,
                outLineColor = color.LightBlue,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.DarkBlue,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray500,
                direction = DRAWABLE_END,
                outLineColor = color.Gray500,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray100,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                outLineColor = color.LightBlue,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.DarkBlue,
                drawable = R.drawable.ic_temp,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                drawable = R.drawable.ic_temp,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                outLineColor = color.LightBlue,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.DarkBlue,
                drawable = R.drawable.ic_temp,
                shape = 50,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                drawable = R.drawable.ic_temp,
                disable = true,
                shape = 50,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.LightBlue,
                backgroundColor = color.Gray300,
            )
            MediumButton(
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
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                direction = DRAWABLE_START,
                drawable = R.drawable.ic_temp,
                textColor = color.LightBlue,
                backgroundColor = color.Gray300,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                direction = DRAWABLE_START,
                drawable = R.drawable.ic_temp,
                textColor = color.Gray500,
                backgroundColor = color.Gray300,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                direction = DRAWABLE_END,
                drawable = R.drawable.ic_temp,
                textColor = color.LightBlue,
                backgroundColor = color.Gray300,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                direction = DRAWABLE_END,
                drawable = R.drawable.ic_temp,
                textColor = color.Gray500,
                backgroundColor = color.Gray300,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray300,
            )
            MediumButton(
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
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray300,
                shape = 50,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                direction = DRAWABLE_CENTER,
                drawable = R.drawable.ic_temp,
                backgroundColor = color.Gray300,
                disable = true,
                shape = 50,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray900,
                backgroundColor = color.Gray300,
                backgroundPressedColor = color.Gray600,
            )
            MediumButton(
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
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray900,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.LightBlue,
            )
            MediumButton(
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
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray900,
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.LightBlue,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                textColor = color.Gray500,
                backgroundColor = color.Gray300,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray900,
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.LightBlue,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                textColor = color.Gray500,
                backgroundColor = color.Gray300,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.LightBlue,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                backgroundColor = color.Gray300,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            MediumButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.LightBlue,
                shape = 50,
            )
            MediumButton(
                onClick = { /*TODO*/ },
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_CENTER,
                backgroundColor = color.Gray300,
                disable = true,
                shape = 50,
            )
        }
    }
}

@Composable
@Preview(
    name = "LargeButtonPreview",
    showBackground = true,
    widthDp = 800,
)
fun LargeButtonPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                backgroundPressedColor = color.DarkBlue,
            )
            LargeButton(
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
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                backgroundPressedColor = color.DarkBlue,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                backgroundColor = color.Gray500,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                backgroundPressedColor = color.DarkBlue,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                backgroundColor = color.Gray500,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.LightBlue,
                backgroundColor = color.Gray100,
                outLineColor = color.LightBlue,
            )
            LargeButton(
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
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                textColor = color.LightBlue,
                backgroundColor = color.Gray100,
                outLineColor = color.LightBlue,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
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
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                textColor = color.LightBlue,
                backgroundColor = color.Gray100,
                outLineColor = color.LightBlue,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
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
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.LightBlue,
                backgroundColor = color.Gray300,
            )
            LargeButton(
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
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                textColor = color.LightBlue,
                backgroundColor = color.Gray300,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                textColor = color.Gray500,
                backgroundColor = color.Gray300,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                textColor = color.LightBlue,
                backgroundColor = color.Gray300,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                textColor = color.Gray500,
                backgroundColor = color.Gray300,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray900,
                backgroundColor = color.Gray300,
                backgroundPressedColor = color.Gray600,
            )
            LargeButton(
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
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray900,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.Gray600,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                textColor = color.Gray500,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                textColor = color.Gray900,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.Gray600,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_START,
                textColor = color.Gray500,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                disable = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                textColor = color.Gray900,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                backgroundPressedColor = color.Gray600,
            )
            LargeButton(
                onClick = { /*TODO*/ },
                text = "버튼",
                drawable = R.drawable.ic_temp,
                direction = DRAWABLE_END,
                textColor = color.Gray500,
                outLineColor = color.Gray500,
                backgroundColor = color.Gray100,
                disable = true,
            )
        }
    }
}