package team.retum.jobis_android.util.compose

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.VibratorManager
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("ServiceCast", "ComposableNaming")
internal fun vibrate(
    context: Context,
    millSeconds: Long = 5L,
) {
    val vibrator = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
    val vibrationEffect = VibrationEffect.createOneShot(
        millSeconds,
        VibrationEffect.DEFAULT_AMPLITUDE,
    )
    val combinedVibration = CombinedVibration.createParallel(vibrationEffect)
    vibrator.vibrate(combinedVibration)
}