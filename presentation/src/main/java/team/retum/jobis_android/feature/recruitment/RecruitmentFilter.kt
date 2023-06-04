package team.retum.jobis_android.feature.recruitment

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.jobis.jobis_android.R
import team.retum.domain.entity.code.CodeEntity
import team.retum.jobis_android.viewmodel.code.CodeViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisCheckBoxColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.retum.jobisui.util.jobisClickable
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.checkbox.JobisCheckBox
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.textfield.TextFieldType
import team.returm.jobisdesignsystem.theme.Body3
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption

@Composable
internal fun RecruitmentFilter(
    codeViewModel: CodeViewModel = hiltViewModel(),
) {

    val state by codeViewModel.container.stateFlow.collectAsState()

    val techs = remember { mutableStateListOf<String>() }
    val techChecks = remember { mutableStateListOf<Boolean>() }

    var techCode by remember { mutableStateOf("") }

    val onTechCodeChanged = { value: String ->
        techCode = value
    }

    val tech = StringBuilder().apply {
        techs.forEach {
            if (techChecks[techs.indexOf(it)]) {
                append(it)
                append(" ")
            }
        }
    }.toString().trim().replace(" ", " | ")

    LaunchedEffect(Unit) {
        codeViewModel.fetchCodes()
    }

    var folded by remember { mutableStateOf(true) }

    val foldedPadding by animateDpAsState(
        targetValue = if (folded) 160.dp
        else (-48).dp,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing,
        )
    )

    val onTechChecked = { index: Int ->
        techChecks[index] = !techChecks[index]
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
                        onValueChanged = onTechCodeChanged,
                        value = techCode,
                        hint = stringResource(id = R.string.search_tech_code),
                        textFieldType = TextFieldType.SEARCH,
                    )
                    Positions(
                        folded = folded,
                        positions = state.codes,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(bottom = foldedPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Column(
                        modifier = Modifier.background(JobisColor.Gray100),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Caption(
                            modifier = Modifier.jobisClickable(
                                rippleEnabled = false,
                                interactionSource = remember { MutableInteractionSource() },
                            ) {
                                if (state.codes.isNotEmpty()) {
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
                                text = tech.ifEmpty { stringResource(id = R.string.company_details_null) },
                                color = JobisColor.LightBlue,
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Techs(
                            techs = techs,
                            techChecks = techChecks,
                            onTechChecked = onTechChecked,
                        )
                    }
                }
            }
            JobisLargeButton(
                text = stringResource(id = R.string.apply),
                color = JobisButtonColor.MainSolidColor,
                onClick = {},
            )
        }
    }
}

@Composable
private fun Positions(
    folded: Boolean,
    positions: List<CodeEntity>,
) {

    var selectedPosition by remember { mutableStateOf("") }

    Column(
        modifier = Modifier

            .padding(bottom = 16.dp)
    ) {
        FlowRow(
            modifier = Modifier.padding(top = 14.dp),
            mainAxisAlignment = MainAxisAlignment.Start,
            crossAxisSpacing = 8.dp,
            mainAxisSpacing = 8.dp,
        ) {
            positions.forEach { item ->
                Position(
                    keyword = item.keyword,
                    selected = selectedPosition == item.keyword,
                ) {
                    selectedPosition = item.keyword
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
            .jobisClickable(
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onSelectedPosition()
            },
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
    techs: List<String>,
    techChecks: List<Boolean>,
    onTechChecked: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(techs) { index, tech ->
            Tech(
                tech = tech,
                checked = techChecks[index],
                onTechChecked = { onTechChecked(index) },
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