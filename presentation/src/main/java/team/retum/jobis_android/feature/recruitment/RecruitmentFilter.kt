package team.retum.jobis_android.feature.recruitment

import android.content.res.Resources
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
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.jobis.jobis_android.R
import team.retum.domain.entity.code.CodeEntity
import team.retum.domain.enums.Type
import team.retum.jobis_android.viewmodel.code.CodeViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.checkbox.JobisCheckBox
import team.returm.jobisdesignsystem.colors.JobisCheckBoxColor
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType
import team.returm.jobisdesignsystem.theme.Body3
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun RecruitmentFilter(
    sheetState: Boolean = false,
    codeViewModel: CodeViewModel = hiltViewModel(),
    onDismissDialog: (jobCode: Long, techCodes: String) -> Unit,
) {

    val state by codeViewModel.container.stateFlow.collectAsStateWithLifecycle()

    val selectedTechs = state.selectedTechs

    var folded by remember { mutableStateOf(false) }

    val onKeywordChanged: (String) -> Unit = { keyword: String ->
        if (folded) folded = false
        codeViewModel.setKeyword(keyword)
    }

    val selectedTech = StringBuilder().apply {
        selectedTechs.forEach {
            append(it.second)
            append(" ")
        }
    }.toString().trim().replace(" ", " | ")

    LaunchedEffect(sheetState) {
        if (sheetState) {
            with(codeViewModel) {
                fetchCodes()
                setType(Type.TECH)
                fetchCodes()
            }
        }
    }

    var positionsHeight by remember { mutableStateOf(0.dp) }

    val foldedOffset by animateDpAsState(
        targetValue = if (folded) (positionsHeight + 12.dp)
        else (-12).dp,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing,
        ),
        label = "",
    )

    val onSelectTech: (Long, String) -> Unit = { code: Long, keyword: String ->
        codeViewModel.onSelectTech(
            code = code,
            keyword = keyword,
        )
    }

    val setOnPositionsHeight: (Int) -> Unit = { positionsHeight = it.dp }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Box {
                Column(
                    modifier = Modifier.padding(
                        top = 20.dp,
                        start = 20.dp,
                        end = 20.dp,
                    ),
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
                    Jobs(
                        folded = folded,
                        positions = state.jobs,
                        codeViewModel = codeViewModel,
                        selectedPositionCode = state.selectedJobCode ?: 0,
                        setOnPositionsHeight = setOnPositionsHeight,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .padding(top = 20.dp)
                        .offset(y = foldedOffset),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.82f)
                            .background(JobisColor.Gray100)
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Caption(
                            modifier = Modifier.jobisClickable {
                                if (state.jobs.isNotEmpty()) {
                                    folded = !folded
                                }
                            },
                            text = stringResource(
                                id = if (folded) R.string.unfold
                                else R.string.choose_position,
                            ),
                            color = JobisColor.Gray600,
                            decoration = TextDecoration.Underline,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
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
                        Techs(
                            techs = state.techs,
                            selectedTechs = selectedTechs,
                            onSelectTech = onSelectTech,
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                JobisLargeButton(
                    text = stringResource(id = R.string.apply),
                    color = JobisButtonColor.MainSolidColor,
                    enabled = (state.selectedJobCode != null || selectedTech.isNotEmpty()),
                    onClick = {
                        onDismissDialog(
                            state.selectedJobCode ?: 0L, getTechCodes(selectedTechs)
                        )
                    },
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun Jobs(
    folded: Boolean,
    positions: List<CodeEntity>,
    codeViewModel: CodeViewModel,
    selectedPositionCode: Long,
    setOnPositionsHeight: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
                .onGloballyPositioned { setOnPositionsHeight(it.size.height.toDp) },
            mainAxisAlignment = MainAxisAlignment.Start,
            crossAxisSpacing = 8.dp,
            mainAxisSpacing = 4.dp,
        ) {
            repeat(positions.size) {

                val code = positions[it].code

                Job(
                    keyword = positions[it].keyword,
                    selected = selectedPositionCode == code,
                ) {
                    if (folded) {
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


private val JobShape = RoundedCornerShape(18.dp)

@Composable
private fun Job(
    keyword: String,
    selected: Boolean,
    onSelectedPosition: () -> Unit,
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 2.dp,
                shape = JobShape,
            )
            .background(
                color = if (selected) JobisColor.LightBlue
                else JobisColor.Gray100,
                shape = JobShape,
            )
            .clip(JobShape)
            .jobisClickable(
                onClick = onSelectedPosition,
                rippleEnabled = true,
            ),
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
    onSelectTech: (code: Long, keyword: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(techs) { tech ->

            val code = tech.code
            val keyword = tech.keyword

            Tech(
                tech = tech.keyword,
                checked = selectedTechs.contains(code to keyword),
                onTechChecked = { onSelectTech(code, keyword) },
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
            verticalAlignment = Alignment.CenterVertically,
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

private fun getTechCodes(
    techCodes: List<Pair<Long, String>>,
): String {
    return StringBuilder().apply {
        techCodes.forEach { append("${it.first} ") }
    }.toString().trim().replace(" ", ",")
}
