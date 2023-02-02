package com.jobis.design_system.button.basic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jobis.design_system.color.color

@Composable
fun BasicSolidButton(
    onClick: () -> Unit,
    text: String,
    width: Int,
    height: Int,
    roundSize: Int,
    textStyle: TextStyle,
    backgroundColor: Color,
    textColor: Color,
    disable: Boolean,
) {
    Box(
        modifier = Modifier
            .size(
                width = width.dp,
                height = height.dp,
            )
            .clickable(
                enabled = disable,
                onClick = onClick,
            )
            .clip(
                shape = RoundedCornerShape(roundSize.dp),
            )
            .background(
                color = backgroundColor,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}

@Composable
fun BasicOutLineButton(
    onClick: () -> Unit,
    text: String,
    width: Int,
    height: Int,
    roundSize: Int,
    textStyle: TextStyle,
    backgroundColor: Color,
    textColor: Color,
    disable: Boolean,
) {
    Box(
        modifier = Modifier
            .size(
                width = width.dp,
                height = height.dp,
            )
            .clickable(
                enabled = disable,
                onClick = onClick,
            )
            .clip(
                shape = RoundedCornerShape(roundSize.dp),
            )
            .border(
                border = BorderStroke(
                    width = 1.5.dp,
                    color = if(disable) color.Gray400
                    else color.LightBlue
                ),
                shape = RoundedCornerShape(roundSize.dp)
            )
            .background(
                color = backgroundColor,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}



