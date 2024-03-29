package team.retum.jobis_android.feature.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.compose.collectAsState
import team.retum.domain.entity.review.ReviewDetailEntity
import team.retum.jobis_android.navigation.NavigationProperties
import team.retum.jobis_android.util.compose.component.Header
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body1
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption

@Composable
internal fun ReviewDetailsScreen(
    reviewId: String,
    getString: (key: String) -> String?,
    reviewDetailsScreenViewModel: ReviewDetailsScreenViewModel = hiltViewModel(),
) {
    val state by reviewDetailsScreenViewModel.collectAsState()

    LaunchedEffect(Unit) {
        reviewDetailsScreenViewModel.fetchReviewDetails(reviewId = reviewId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Header(
            text = stringResource(
                id = R.string.review_details_review,
                getString(NavigationProperties.WRITER) ?: "",
            ),
        )
        Spacer(modifier = Modifier.height(30.dp))
        InterviewReviews(interviewEntities = state.reviewDetails)
    }
}

@Composable
private fun InterviewReviews(
    interviewEntities: List<ReviewDetailEntity>,
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        items(interviewEntities) {
            InterviewReview(
                position = it.question,
                title = it.answer,
                content = it.area,
            )
        }
    }
}

@Composable
private fun InterviewReview(
    position: String,
    title: String,
    content: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        Caption(
            text = position,
            color = JobisColor.ToastBlue,
        )
        Body1(
            text = title,
            color = JobisColor.Gray700,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Body4(
            text = content,
            color = JobisColor.Gray900,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            color = JobisColor.Gray400,
        )
    }
}
