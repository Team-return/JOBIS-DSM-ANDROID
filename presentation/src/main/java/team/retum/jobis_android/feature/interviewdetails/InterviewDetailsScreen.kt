package team.retum.jobis_android.feature.interviewdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body1
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption

@Composable
internal fun InterviewDetailsScreen(
    reviewId: String,
    navController: NavController,
) {

    val list = listOf(
        InterviewEntity(
            position = "position",
            title = "title",
            content = "content",
        ),
        InterviewEntity(
            position = "position",
            title = "title",
            content = "content",
        ),
        InterviewEntity(
            position = "position",
            title = "title",
            content = "content",
        ),
    )

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Header(text = "header")
        Spacer(modifier = Modifier.height(30.dp))
        InterviewReviews(
            interviewEntities = list,
        )
    }
}

@Composable
private fun InterviewReviews(
    interviewEntities: List<InterviewEntity>,
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        items(interviewEntities){
            InterviewReview(
                position = it.position,
                title = it.title,
                content = it.content,
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
            color = JobisColor.Checked,
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

data class InterviewEntity(
    val position: String,
    val title: String,
    val content: String,
)