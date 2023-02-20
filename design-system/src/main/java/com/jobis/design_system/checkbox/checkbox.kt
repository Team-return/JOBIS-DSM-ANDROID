package com.jobis.design_system.checkbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.R
import com.jobis.design_system.checkbox.size.CheckBoxSize
import com.jobis.design_system.color.color
import com.jobis.design_system.util.MediumBorder
import com.jobis.design_system.util.SmallShape
import com.jobis.design_system.util.click

@Preview(
    showBackground = true,
)
@Composable
fun CheckboxPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        var check by remember { mutableStateOf(false) }
        Checkbox(
            onCheck = { check = !check },
            isCheck = check,
            disabled = false,
        )
    }
}

@Composable
fun Checkbox(
    onCheck: (Boolean) -> Unit,
    isCheck: Boolean,
    disabled: Boolean = false,
) {

    val interactionSource = remember { MutableInteractionSource() }

    val background = if (disabled) {
        if (isCheck) color.Gray500
        else color.Gray400
    } else if (isCheck) color.CheckBlue
    else color.Gray100

    val outLineColor = if (disabled) {
        if (isCheck) color.Gray500
        else color.Gray500
    } else if (isCheck) color.CheckBlue
    else color.Gray400

    Box(
        modifier = CheckBoxSize
            .border(
                width = MediumBorder,
                color = outLineColor,
                shape = SmallShape
            )
            .background(
                color = background,
                shape = SmallShape
            )
            .clickable {
                onCheck(isCheck)
            },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = null,
        )
    }
}