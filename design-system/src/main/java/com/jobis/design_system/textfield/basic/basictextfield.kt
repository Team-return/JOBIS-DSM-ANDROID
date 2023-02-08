package com.jobis.design_system.textfield.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jobis.design_system.color.BasicBoxTextFieldColor
import com.jobis.design_system.color.BasicUnderLineTextFieldColor
import com.jobis.design_system.color.BoxTextFieldColor
import com.jobis.design_system.color.UnderLineTextFieldColor
import com.jobis.design_system.textfield.size.DefaultBoxTextFieldSize
import com.jobis.design_system.textfield.size.DefaultUnderLineTextFieldSize
import com.jobis.design_system.typography.typography
import com.jobis.design_system.util.*

@Stable
val TextFieldTextModifier = Modifier
    .padding(
        start = 16.dp,
        bottom = 1.dp,
    )
    .fillMaxWidth(0.87f)

@Composable
fun BasicBoxTextField(
    hint: String,
    value: String,
    onValueChanged: (String) -> Unit,
    drawable: Int = 0,
    color: BasicBoxTextFieldColor = BoxTextFieldColor.DefaultBoxTextFieldColor,
    textStyle: TextStyle = typography.body1,
    helperText: String = "",
    fieldText: String = "",
    isError: Boolean = false,
    disable: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isFocused by remember { mutableStateOf(false) }

    var isPassword by remember { mutableStateOf(false) }

    val fieldTextColor = if (disable) color.disabledColor!!.fieldTextColor
    else if (isError) color.errorColor
    else color.fieldTextColor

    val outLineColor = if (disable) color.disabledColor!!.outLineColor
    else if (isError) color.errorColor
    else if (isFocused) color.focusedColor
    else color.outLineColor

    val backgroundColor = if (disable) color.disabledColor!!.backgroundColor
    else color.backgroundColor

    val textColor = if (disable) color.disabledColor!!.textColor
    else color.textColor

    val helperTextColor = if (isError) color.errorColor
    else color.helperTextColor

    Column {
        Text(
            text = fieldText,
            color = fieldTextColor!!,
            style = typography.body4,
        )
        Spacer(modifier = MediumHeightSpacer)
        Box(
            modifier = DefaultBoxTextFieldSize
                .border(
                    width = 1.dp,
                    color = outLineColor!!,
                    shape = SmallShape,
                )
                .background(
                    color = backgroundColor,
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BasicTextField(
                    modifier = TextFieldTextModifier
                        .onFocusChanged {
                            isFocused = it.isFocused
                        },
                    value = value,
                    onValueChange = onValueChanged,
                    singleLine = true,
                    visualTransformation = if (isPassword) PasswordVisualTransformation()
                    else VisualTransformation.None,
                    textStyle = textStyle.copy(
                        color = textColor,
                    ),
                    enabled = !disable,
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                color = color.hintColor!!,
                                style = textStyle,
                            )
                        }
                        innerTextField()
                    }
                )
                Spacer(modifier = MediumWidthSpacer)
                Image(
                    painterResource(id = drawable),
                    contentDescription = null,
                    modifier = DrawableSize
                        .click(
                            onClick = { isPassword = !isPassword },
                            disable = disable,
                            interactionSource = interactionSource,
                        ),
                )
            }
        }
        Spacer(modifier = MediumHeightSpacer)
        if (!disable) {
            Text(
                text = helperText,
                color = helperTextColor!!,
                style = typography.caption,
            )
        }
    }
}

@Composable
fun BasicUnderLineTextField(
    hint: String,
    value: String,
    onValueChanged: (String) -> Unit,
    drawable: Int = 0,
    color: BasicUnderLineTextFieldColor = UnderLineTextFieldColor.DefaultUnderLineTextFieldColor,
    textStyle: TextStyle = typography.body1,
    helperText: String = "",
    fieldText: String = "",
    isError: Boolean = false,
    disable: Boolean = false,
) {

    val interactionSource = remember { MutableInteractionSource() }
    var isFocused by remember { mutableStateOf(false) }

    var isPassword by remember { mutableStateOf(false) }

    val fieldTextColor = if (disable) color.disabledColor!!.fieldTextColor
    else if (isError) color.errorColor
    else color.fieldTextColor

    val underLineColor = if (disable) color.disabledColor!!.underLineColor
    else if (isError) color.errorColor
    else if (isFocused) color.focusedColor
    else color.underLineColor

    val textColor = if (disable) color.disabledColor!!.textColor
    else color.textColor

    val helperTextColor = if (isError) color.errorColor
    else color.helperTextColor

    Column {
        Text(
            text = fieldText,
            color = fieldTextColor!!,
            style = typography.body4,
        )
        Spacer(modifier = MediumHeightSpacer)
        Box(
            modifier = DefaultBoxTextFieldSize,
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BasicTextField(
                    modifier = TextFieldTextModifier
                        .onFocusChanged {
                            isFocused = it.isFocused
                        },
                    value = value,
                    onValueChange = onValueChanged,
                    singleLine = true,
                    visualTransformation = if (isPassword) PasswordVisualTransformation()
                    else VisualTransformation.None,
                    textStyle = textStyle.copy(
                        color = textColor,
                    ),
                    enabled = !disable,
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                color = color.hintColor!!,
                                style = textStyle,
                            )
                        }
                        innerTextField()
                    }
                )
                Spacer(modifier = MediumWidthSpacer)
                Image(
                    painterResource(id = drawable),
                    contentDescription = null,
                    modifier = DrawableSize
                        .click(
                            onClick = { isPassword = !isPassword },
                            disable = disable,
                            interactionSource = interactionSource,
                        ),
                )
            }
        }
        Column(modifier = DefaultUnderLineTextFieldSize.background(color = underLineColor!!)) {}
        Spacer(modifier = MediumHeightSpacer)
        if (!disable) {
            Text(
                text = helperText,
                color = helperTextColor!!,
                style = typography.caption,
            )
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        var id by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }

        BasicBoxTextField(
            hint = "아이디",
            value = id,
            onValueChanged = { id = it },
        )
    }
}
