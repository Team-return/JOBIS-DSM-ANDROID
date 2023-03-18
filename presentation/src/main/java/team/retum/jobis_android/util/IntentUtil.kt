package team.retum.jobis_android.util

import android.content.Context
import android.content.Intent

fun <T>startActivity(
    context: Context,
    to: Class<T>,
){
    context.startActivity(
        Intent(context, to)
    )
}