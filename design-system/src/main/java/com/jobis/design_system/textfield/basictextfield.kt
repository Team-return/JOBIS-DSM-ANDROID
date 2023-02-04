package com.jobis.design_system.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.jobis.design_system.color.color
import com.jobis.design_system.typography.typography

@Composable
@Preview(
    showSystemUi = true,
)
fun Prv() {
    Column {
        BasicBoxTextField(
            hint = "PlaceHolder",
            stateValue = "",
            isError = false,
            errorText = ""
        )
    }
}

@Composable
fun BasicBoxTextField(
    hint: String,
    stateValue: String = "",
    isError: Boolean = false,
    errorText: String = "",
    drawable: Int = 0,
) {

    var value by remember { mutableStateOf(stateValue) }
    var isFocused by remember { mutableStateOf(false) }

    Column() {
        BasicTextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier
                .size(
                    width = 376.dp,
                    height = 40.dp,
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) color.Gray600
                    else if (isError) color.Red
                    else color.Gray400,
                    shape = RoundedCornerShape(4.dp)
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Row() {
                        if(value.isEmpty()) {
                            Text(
                                text = hint,
                                color = color.Gray600,
                                modifier = Modifier.padding(top = 2.dp)
                                    .fillMaxWidth(0.9f),
                            )
                        }
                        if(drawable != 0) {
                            Image(
                                painter = painterResource(id = drawable),
                                contentDescription = null,
                            )
                        }
                    }
                    innerTextField()
                }
            }
        )
        Spacer(modifier = Modifier.padding(top = 4.dp))
        Text(
            text = if (isError) errorText
            else "",
            style = typography.caption,
            color = color.Red,
        )
    }
}