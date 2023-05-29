package team.retum.jobis_android.util.compose

@Deprecated("로직 리팩토링 필요", ReplaceWith("isError && textFieldValue.length == inputValue.length"))
fun clearTextFieldError(
    isError: Boolean,
    inputValue: String,
    textFieldValue: String,
): Boolean = isError && textFieldValue.length == inputValue.length