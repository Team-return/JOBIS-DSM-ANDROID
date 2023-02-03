package com.jobis.design_system.button.basic

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jobis.design_system.color.color
import com.jobis.design_system.typography.typography

@Composable
fun BasicDrawableSolidButton(
    onClick: () -> Unit,
    drawable: Int,
    width: Int,
    height: Int,
    roundSize: Int,
    backgroundColor: Color,
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
        Image(
            painter = painterResource(
                id = drawable,
            ),
            contentDescription = null,
        )
    }
}

@Composable
fun BasicDrawableOutLineButton(
    onClick: () -> Unit,
    drawable: Int,
    width: Int,
    height: Int,
    roundSize: Int,
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
                    color = if (disable) color.Gray400
                    else color.LightBlue
                ),
                shape = RoundedCornerShape(roundSize.dp)
            )
            .background(
                color = color.Gray100,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(
                id = drawable,
            ),
            contentDescription = null,
        )
    }
}

@Composable
fun BasicDrawableTextSolidButton(
    onClick: () -> Unit,
    text: String,
    textColor: Color,
    drawable: Int,
    direction: Int,
    width: Int,
    height: Int,
    roundSize: Int,
    backgroundColor: Color,
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
        Row {
            when (direction) {
                1 -> {
                    Image(
                        painter = painterResource(
                            id = drawable,
                        ),
                        modifier = Modifier
                            .size(
                                width = 24.dp,
                                height = 26.dp,
                            )
                            .align(alignment = Alignment.CenterVertically),
                        contentDescription = null,
                    )
                    Text(
                        text = text,
                        style = typography.heading6,
                        color = textColor,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
                2 -> {
                    Text(
                        text = text,
                        style = typography.heading6,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Image(
                        painter = painterResource(
                            id = drawable,
                        ),
                        modifier = Modifier
                            .size(
                                width = 24.dp,
                                height = 26.dp,
                            )
                            .align(alignment = Alignment.CenterVertically),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
fun BasicDrawableTextOutLineButton(
    onClick: () -> Unit,
    text: String,
    textColor: Color,
    drawable: Int,
    direction: Int,
    width: Int,
    height: Int,
    roundSize: Int,
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
                    color = if (disable) color.Gray400
                    else color.LightBlue
                ),
                shape = RoundedCornerShape(roundSize.dp)
            )
            .background(
                color = color.Gray100,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row {
            when (direction) {
                1 -> {
                    Image(
                        painter = painterResource(
                            id = drawable,
                        ),
                        modifier = Modifier
                            .size(
                                width = 24.dp,
                                height = 26.dp,
                            )
                            .align(alignment = Alignment.CenterVertically),
                        contentDescription = null,
                    )
                    Text(
                        text = text,
                        style = typography.heading6,
                        color = if (disable) color.Gray500
                        else textColor,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
                2 -> {
                    Text(
                        text = text,
                        style = typography.heading6,
                        color = if (disable) color.Gray500
                        else textColor,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Image(
                        painter = painterResource(
                            id = drawable,
                        ),
                        modifier = Modifier
                            .size(
                                width = 24.dp,
                                height = 26.dp,
                            )
                            .align(alignment = Alignment.CenterVertically),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}



