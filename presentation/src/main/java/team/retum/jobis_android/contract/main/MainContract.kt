package team.retum.jobis_android.contract.main

import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class MainState(
    val autoSignInOption: Boolean = false,
): State

sealed class MainSideEffect: SideEffect{

}