package team.retum.jobis_android.util

object Regex {
    const val PASSWORD = "^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-z0-9!@#\$%^&*]{8,16}\$"
    const val EMAIL = "^.+@dsm.hs.kr$"
}