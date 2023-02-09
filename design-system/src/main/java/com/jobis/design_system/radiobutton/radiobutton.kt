package com.jobis.design_system.radiobutton

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jobis.design_system.checkbox.size.CheckBoxSize
import com.jobis.design_system.color.color
import com.jobis.design_system.util.SmallBorder
import com.jobis.design_system.util.click

@Preview(
    showBackground = true,
)
@Composable
fun RadioButtonPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var check by remember { mutableStateOf(false) }
        RadioButton(
            onCheck = { check = !check },
            isCheck = check,
            disabled = false,
        )
    }
}

@Composable
fun RadioButton(
    onCheck: () -> Unit,
    isCheck: Boolean,
    disabled: Boolean = false,
) {

    val interactionSource = remember { MutableInteractionSource() }

    val background = if (disabled) {
        if (isCheck) color.Gray500
        else color.Transparent
    } else if (isCheck) color.CheckBlue
    else color.Transparent

    val outLineColor = if (disabled) {
        if (isCheck) color.Gray500
        else color.Gray500
    } else if (isCheck) color.CheckBlue
    else color.Gray400

    Box(
        modifier = CheckBoxSize
            .border(
                width = SmallBorder,
                color = outLineColor,
                shape = CircleShape
            )
            .click(
                onClick = onCheck,
                interactionSource = interactionSource,
                disable = disabled,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .size(
                    width = 10.dp,
                    height = 10.dp,
                )
                .background(
                    color = background,
                    shape = CircleShape,
                )
        ) {}
    }
}