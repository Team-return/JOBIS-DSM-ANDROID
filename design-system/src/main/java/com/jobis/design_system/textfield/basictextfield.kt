package com.jobis.design_system.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.jobis.design_system.color.color
import com.jobis.design_system.typography.typography

@Composable
fun BasicBoxTextField(
    hint: String,
    stateValue: String = "",
    isError: Boolean = false,
    helperText: String = "",
    drawable: Int = 0,
    fieldText: String,
    disable: Boolean = false
) {

    var value by remember { mutableStateOf(stateValue) }
    var isFocused by remember { mutableStateOf(false) }

    Column() {
        Text(
            text = fieldText,
            style = typography.body4,
            color = if (disable) color.Gray500
            else color.Gray900
        )
        Spacer(modifier = Modifier.height(8.dp))
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
                .background(
                    color = if (disable) color.Gray400
                    else color.Gray100,
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            enabled = !disable,
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Row() {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                color = if (disable) color.Gray400
                                else color.Gray600,
                                style = typography.body1,
                                modifier = Modifier
                                    .padding(bottom = 1.dp)
                                    .fillMaxWidth(0.9f),
                            )
                        }
                        if (drawable != 0) {
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
            text = helperText,
            style = typography.caption,
            color = if (isError) color.Red
            else color.Gray600,
        )
    }
}

@Composable
fun BasicUnderLineTextField(
    hint: String,
    stateValue: String = "",
    isError: Boolean = false,
    helperText: String = "",
    drawable: Int = 0,
    fieldText: String,
    disable: Boolean = false,
) {

    var value by remember { mutableStateOf(stateValue) }
    var isFocused by remember { mutableStateOf(false) }

    Column() {
        Text(
            text = fieldText,
            style = typography.body4,
            color = if (disable) color.Gray500
            else color.Gray900
        )
        BasicTextField(
            value = value,
            onValueChange = { value = it },
            enabled = !disable,
            singleLine = true,
            modifier = Modifier.onFocusChanged {
                isFocused = it.isFocused
            },
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Row() {
                        if(value.isEmpty()) {
                            Text(
                                text = hint,
                                color = if (disable) color.Gray400
                                else color.Gray600,
                                style = typography.body1,
                                modifier = Modifier
                                    .padding(bottom = 1.dp)
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
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier.size(
                width = 372.dp,
                height = 1.dp,
            ).background(
                color = if (isFocused) color.LightBlue
                else if(isError) color.Red
                else if(disable) color.Gray400
                else color.Gray600
            )
        )
        Spacer(modifier = Modifier.padding(top = 4.dp))
        Text(
            text = helperText,
            style = typography.caption,
            color = if (isError) color.Red
            else color.Gray600,
        )
    }
}
