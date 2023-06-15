package team.retum.jobis_android.feature.recruitment

import android.content.res.Resources
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.jobis.jobis_android.R
import team.retum.domain.entity.code.CodeEntity
import team.retum.domain.param.code.Type
import team.retum.jobis_android.viewmodel.code.CodeViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisCheckBoxColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.checkbox.JobisCheckBox
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType
import team.returm.jobisdesignsystem.theme.Body3
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun RecruitmentFilter(
    codeViewModel: CodeViewModel = hiltViewModel(),
    onDismissDialog: (Long?, String?) -> Unit,
) {

    val state by codeViewModel.container.stateFlow.collectAsState()

    val techs = state.techs

    val selectedTechCodes = remember { mutableStateListOf<Pair<Long, String>>() }

    val onKeywordChanged = { keyword: String ->
        codeViewModel.setKeyword(keyword)
    }

    val selectedTech = StringBuilder().apply {
        selectedTechCodes.forEach {
            append(it.second)
            append(" ")
        }
    }.toString().trim().replace(" ", " | ")

    LaunchedEffect(Unit) {
        codeViewModel.fetchCodes()
        codeViewModel.setType(type = Type.TECH)
        codeViewModel.fetchCodes()
    }

    var folded by remember { mutableStateOf(true) }

    var positionsHeight by remember { mutableStateOf(0.dp) }

    val foldedOffset by animateDpAsState(
        targetValue = if (!folded) (positionsHeight + 12.dp)
        else (-12).dp,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing,
        )
    )

    val onTechChecked = { techCode: Long, techName: String ->
        if (selectedTechCodes.contains(techCode to techName)) {
            selectedTechCodes.remove(techCode to techName)
        } else {
            selectedTechCodes.add(techCode to techName)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
        ) {
            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Divider(
                        modifier = Modifier
                            .width(32.dp)
                            .height(2.dp),
                        color = JobisColor.Gray500,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Body4(
                        text = stringResource(id = R.string.tech_code_filter),
                        color = JobisColor.Gray600,
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp),
                        color = JobisColor.Gray500,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    JobisBoxTextField(
                        color = JobisTextFieldColor.MainColor,
                        onValueChanged = onKeywordChanged,
                        value = state.keyword ?: "",
                        hint = stringResource(id = R.string.search_tech_code),
                        textFieldType = TextFieldType.SEARCH,
                    )
                    Positions(
                        folded = folded,
                        positions = state.jobs,
                        codeViewModel = codeViewModel,
                        selectedPositionCode = state.parentCode ?: 0,
                    ) {
                        positionsHeight = it.dp
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .offset(y = foldedOffset),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.82f)
                            .background(JobisColor.Gray100)
                            .padding(top = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Caption(
                            modifier = Modifier.jobisClickable {
                                if (state.jobs.isNotEmpty()) {
                                    folded = !folded
                                }
                            },
                            text = stringResource(
                                id = if (folded) R.string.choose_position
                                else R.string.unfold,
                            ),
                            color = JobisColor.Gray600,
                            decoration = TextDecoration.Underline,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Body4(
                                text = stringResource(id = R.string.chose),
                                color = JobisColor.Gray600,
                            )
                            Body4(
                                text = selectedTech.ifEmpty { stringResource(id = R.string.company_details_null) },
                                color = JobisColor.LightBlue,
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Techs(
                            techs = techs,
                            selectedTechs = selectedTechCodes,
                            onTechChecked = onTechChecked,
                        )
                    }
                }
            }
            JobisLargeButton(
                text = stringResource(id = R.string.apply),
                color = JobisButtonColor.MainSolidColor,
                enabled = (state.parentCode != null || selectedTech.isNotEmpty())
            ) {
                onDismissDialog(state.parentCode, getTechCode(selectedTechCodes))
            }
        }
    }
}

@Composable
private fun Positions(
    folded: Boolean,
    positions: List<CodeEntity>,
    codeViewModel: CodeViewModel,
    selectedPositionCode: Long,
    setOnPositionsHeight: (Int) -> Unit,
) {

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        FlowRow(
            modifier = Modifier
                .padding(top = 14.dp)
                .onGloballyPositioned {
                    setOnPositionsHeight(it.size.height.toDp)
                },
            mainAxisAlignment = MainAxisAlignment.Start,
            crossAxisSpacing = 8.dp,
            mainAxisSpacing = 4.dp,
        ) {
            repeat(positions.size) {

                val code = positions[it].code

                Position(
                    keyword = positions[it].keyword,
                    selected = selectedPositionCode == code,
                ) {
                    if (!folded) {
                        with(codeViewModel) {
                            setType(Type.TECH)
                            setParentCode(code)
                            fetchCodes()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Position(
    keyword: String,
    selected: Boolean,
    onSelectedPosition: () -> Unit,
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(18.dp)
            )
            .background(
                color = if (selected) JobisColor.LightBlue
                else JobisColor.Gray100,
                shape = RoundedCornerShape(18.dp)
            )
            .clip(RoundedCornerShape(18.dp))
            .jobisClickable(onClick = onSelectedPosition),
        contentAlignment = Alignment.Center,
    ) {
        Body4(
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 4.dp,
            ),
            text = keyword,
            color = if (selected) JobisColor.Gray100
            else JobisColor.Gray600,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun Techs(
    techs: List<CodeEntity>,
    selectedTechs: List<Pair<Long, String>>,
    onTechChecked: (Long, String) -> Boolean,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(techs) { tech ->

            val code = tech.code
            val keyword = tech.keyword

            Tech(
                tech = tech.keyword,
                checked = selectedTechs.contains(code to keyword),
                onTechChecked = { onTechChecked(code, keyword) },
            )
        }
    }
}

@Composable
private fun Tech(
    tech: String,
    checked: Boolean,
    onTechChecked: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,

            ) {
            JobisCheckBox(
                color = JobisCheckBoxColor.MainColor,
                isChecked = checked,
                onChecked = onTechChecked,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Body3(text = tech)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            color = JobisColor.Gray400,
        )
    }
}

private val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()

private fun getTechCode(
    techCodes: List<Pair<Long, String>>,
): String {
    return StringBuilder().apply { techCodes.forEach { append("${it.first} ") } }.toString().trim().replace(" ", ",")
}
