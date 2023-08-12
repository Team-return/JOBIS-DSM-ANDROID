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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.root.JobisAppState
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisDropDownColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.button.JobisSmallIconButton
import team.returm.jobisdesignsystem.dropdown.JobisDropDown
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Heading6

@Composable
internal fun PostReviewScreen(
    appState: JobisAppState,
) {

    // TODO 더미
    val reviews = remember {
        mutableStateListOf(
            Review(
                title = "title",
                question = "",
                answer = "",
            )
        )
    }

    // TODO 더미
    val onAddButtonClicked = {
        reviews.add(
            Review(
                title = "title",
                question = "",
                answer = "",
            )
        )
        Unit
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
                    reviews = reviews,
                    onAddButtonClicked = onAddButtonClicked,
                )
            }
            Column {
                Spacer(modifier = Modifier.weight(1f))
                JobisLargeButton(
                    text = stringResource(id = R.string.complete),
                    color = JobisButtonColor.MainSolidColor,
                    onClick = {},
                )
                Spacer(modifier = Modifier.height(44.dp))
            }
        }
    }
}

@Composable
private fun ReviewInputs(
    reviews: SnapshotStateList<Review>,
    onAddButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(bottom = 88.dp),
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(30.dp)) {
            items(reviews) {

                val index = reviews.indexOf(it)

                PostReviewCard(
                    title = it.title,
                    question = reviews[index].question,
                    onQuestionChanged = { question ->
                        reviews[index] = reviews[index].copy(
                            question = question,
                        )
                    },
                    answer = reviews[index].answer,
                    onAnswerChanged = { answer ->
                        reviews[index] = reviews[index].copy(
                            answer = answer,
                        )
                    },
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
    onQuestionChanged: (String) -> Unit,
    answer: String,
    onAnswerChanged: (String) -> Unit,
) {
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
}

@Composable
private fun ReviewTitle(
    title: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        Heading6(text = title)
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier.width(120.dp)) {
            JobisDropDown(
                color = JobisDropDownColor.MainColor,
                itemList = listOf(),
                onItemSelected = {},
                title = stringResource(id = R.string.post_review_position),
            )
        }
    }
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
        fieldText = stringResource(id = R.string.post_review_question),
    )
    Spacer(modifier = Modifier.height(20.dp))
    JobisBoxTextField(
        onValueChanged = onAnswerChanged,
        value = answer,
        hint = stringResource(id = R.string.post_review_answer_hint),
        fieldText = stringResource(id = R.string.post_review_answer),
    )
}

data class Review(
    val title: String,
    var question: String,
    var answer: String,
)