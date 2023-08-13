package team.retum.jobis_android.contract.code

import team.retum.domain.entity.code.CodeEntity
import team.retum.domain.param.code.Type
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State

data class CodeState(
    val jobs: List<CodeEntity> = emptyList(),
    val techs: List<CodeEntity> = emptyList(),
    val businessAreas: List<CodeEntity> = emptyList(),
    val keyword: String? = null,
    val type: Type = Type.JOB,
    val parentCode: Long? = null,
): State

sealed class CodeSideEffect: SideEffect{

}