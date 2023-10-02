package team.retum.jobis_android.contract.code

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import team.retum.domain.entity.code.CodeEntity
import team.retum.domain.enums.Type
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class CodeState(
    val jobs: List<CodeEntity> = emptyList(),
    val techs: MutableList<CodeEntity> = mutableListOf(),
    val businessAreas: List<CodeEntity> = emptyList(),
    val selectedTechs: SnapshotStateList<Pair<Long, String>> = mutableStateListOf(),
    val keyword: String? = null,
    val type: Type = Type.JOB,
    val selectedJobCode: Long? = null,
) : State

sealed class CodeSideEffect : SideEffect