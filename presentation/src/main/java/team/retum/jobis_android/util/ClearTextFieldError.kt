package team.retum.jobis_android.util

fun clearTextFieldError(
    isError: Boolean,
    inputValue: String,
    textFieldValue: String,
): Boolean = isError && textFieldValue.length == inputValue.length