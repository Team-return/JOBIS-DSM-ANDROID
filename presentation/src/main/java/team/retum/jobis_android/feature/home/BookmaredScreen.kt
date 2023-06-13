package team.retum.jobis_android.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.domain.entity.bookmark.BookmarkedRecruitmentEntity
import team.retum.jobis_android.feature.recruitment.Header
import team.retum.jobis_android.viewmodel.bookmark.BookmarkViewModel
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body3
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption

@Composable
internal fun BookmarkedScreen(
    navController: NavController,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
) {

    val bookmarks = remember {
        mutableStateListOf<BookmarkedRecruitmentEntity>()
    }

    LaunchedEffect(Unit) {
        bookmarkViewModel.fetchBookmarkedRecruitments()
        bookmarkViewModel.container.stateFlow.collect {
            bookmarks.clear()
            bookmarks.addAll(it.bookmarkedRecruitments)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 48.dp,
                start = 24.dp,
                end = 24.dp,
            )
    ) {
        Header(text = stringResource(id = R.string.bookmarked_recruitments))
        BookmarkedRecruitments(bookmarks = bookmarks) {
            bookmarks.remove(it)
            bookmarkViewModel.bookmarkRecruitment(recruitmentId = it.recruitmentId)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BookmarkedRecruitments(
    bookmarks: MutableList<BookmarkedRecruitmentEntity>,
    onSwipeItem: (BookmarkedRecruitmentEntity) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 30.dp)
    ) {

        items(
            items = bookmarks,
            key = { it.recruitmentId },
            itemContent = { item ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        onSwipeItem(item)
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        SwipeToDismiss()
                    },
                    dismissContent = {
                        BookmarkedRecruitment(
                            companyName = item.companyName,
                            createdAt = item.createdAt,
                        )
                    }
                )
            }
        )
    }
}

@Composable
private fun BookmarkedRecruitment(
    companyName: String,
    createdAt: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(shape = RoundedCornerShape(12.dp))
            .background(
                color = JobisColor.Gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Body4(text = companyName)
        Caption(
            text = stringResource(id = R.string.bookmarked_date, createdAt.split('T')[0]),
            color = JobisColor.Gray600,
        )
    }
}

@Composable
private fun SwipeToDismiss() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(JobisColor.Gray100, JobisColor.Red),
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Body3(
            text = "삭제",
            color = JobisColor.Gray100,
        )
    }
}