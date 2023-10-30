package team.retum.jobis_android.feature.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobis.jobis_android.R
import team.retum.domain.entity.code.CodeEntity
import team.retum.domain.enums.Type
import team.retum.domain.param.review.QnaElementParam
import team.retum.jobis_android.LocalAppState
import team.retum.jobis_android.contract.review.ReviewSideEffect
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.viewmodel.code.CodeViewModel
import team.retum.jobis_android.viewmodel.review.ReviewViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.button.JobisSmallIconButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.colors.JobisDropDownColor
import team.returm.jobisdesignsystem.dropdown.JobisDropDown
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Heading6

@Composable
internal fun PostReviewScreen(
    companyId: Long,
    navigatePopBackStack: () -> Unit,
    reviewViewModel: ReviewViewModel = hiltViewModel(),
    codeViewModel: CodeViewModel = hiltViewModel(),
) {
    val codeState by codeViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val reviewState by reviewViewModel.container.stateFlow.collectAsStateWithLifecycle()

    val appState = LocalAppState.current

    val successPostReviewMessage = stringResource(id = R.string.post_review_success_toast_message)

    LaunchedEffect(Unit) {
        with(codeViewModel) {
            setType(Type.TECH)
            fetchCodes()
        }

        with(reviewViewModel) {
            addQnaElement()
            setCompanyId(companyId)

            container.sideEffectFlow.collect {
                when (it) {
                    is ReviewSideEffect.SuccessPostReview -> {
                        appState.showSuccessToast(message = successPostReviewMessage)
                        navigatePopBackStack()
                    }

                    is ReviewSideEffect.Exception -> {
                        appState.showErrorToast(message = it.message)
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(48.dp))
            Header(text = stringResource(id = R.string.post_review_header))
            Spacer(modifier = Modifier.height(30.dp))
            ReviewInputs(
                qnaElements = reviewState.qnaElements,
                codes = codeState.techs,
                onAddButtonClicked = reviewViewModel::addQnaElement,
                onQuestionChanged = reviewViewModel::setQuestion,
                onAnswerChanged = reviewViewModel::setAnswer,
                onItemSelected = reviewViewModel::setJobCode,
            )
        }
        Column {
            Spacer(modifier = Modifier.weight(1f))
            JobisLargeButton(
                text = stringResource(id = R.string.complete),
                color = JobisButtonColor.MainSolidColor,
                onClick = reviewViewModel::postReview,
            )
            Spacer(modifier = Modifier.height(44.dp))
        }
    }
}

@Composable
private fun ReviewInputs(
    qnaElements: List<QnaElementParam>,
    codes: List<CodeEntity>,
    onQuestionChanged: (String, Int) -> Unit,
    onAnswerChanged: (String, Int) -> Unit,
    onAddButtonClicked: () -> Unit,
    onItemSelected: (Long, Int) -> Unit,
) {
    Column(modifier = Modifier.padding(bottom = 88.dp)) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(30.dp)) {
            itemsIndexed(qnaElements) { index: Int, qnaElement: QnaElementParam ->
                PostReviewCard(
                    title = stringResource(id = R.string.post_review_question, index + 1),
                    question = qnaElement.question,
                    codes = codes,
                    onQuestionChanged = { onQuestionChanged(it, index) },
                    answer = qnaElement.answer,
                    onAnswerChanged = { onAnswerChanged(it, index) },
                    onItemSelected = { onItemSelected(codes[index].code, index) },
                )
                Spacer(modifier = Modifier.height(30.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = JobisColor.Gray400,
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 50.dp,
                            bottom = 16.dp,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    JobisSmallIconButton(
                        drawable = R.drawable.ic_add_blue,
                        onClick = onAddButtonClicked,
                        color = JobisButtonColor.MainShadowColor,
                        shadow = true,
                        imageContentDescription = stringResource(id = R.string.content_description_icon_add),
                    )
                }
            }
        }
    }
}

@Composable
private fun PostReviewCard(
    title: String,
    question: String,
    codes: List<CodeEntity>,
    onQuestionChanged: (String) -> Unit,
    answer: String,
    onAnswerChanged: (String) -> Unit,
    onItemSelected: (Int) -> Unit,
) {
    Box {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ReviewTitle(title = title)
            Spacer(modifier = Modifier.height(32.dp))
            PostReviewInputs(
                question = question,
                onQuestionChanged = onQuestionChanged,
                answer = answer,
                onAnswerChanged = onAnswerChanged,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Box(modifier = Modifier.width(120.dp)) {
                JobisDropDown(
                    color = JobisDropDownColor.MainColor,
                    itemList = codes.map { it.keyword },
                    title = stringResource(id = R.string.post_review_position),
                    onItemSelected = onItemSelected,
                )
            }
        }
    }
}

@Composable
private fun ReviewTitle(
    title: String,
) {
    Heading6(
        modifier = Modifier.fillMaxWidth(),
        text = title,
    )
}

@Composable
private fun PostReviewInputs(
    question: String,
    onQuestionChanged: (String) -> Unit,
    answer: String,
    onAnswerChanged: (String) -> Unit,
) {
    JobisBoxTextField(
        onValueChanged = onQuestionChanged,
        value = question,
        hint = stringResource(id = R.string.post_review_question_hint),
        fieldText = stringResource(id = R.string.post_review_interview_question),
    )
    Spacer(modifier = Modifier.height(20.dp))
    JobisBoxTextField(
        onValueChanged = onAnswerChanged,
        value = answer,
        hint = stringResource(id = R.string.post_review_answer_hint),
        fieldText = stringResource(id = R.string.post_review_answer),
    )
}
