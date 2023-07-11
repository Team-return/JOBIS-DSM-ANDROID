package team.retum.jobis_android.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.domain.entity.bookmark.BookmarkedRecruitmentEntity
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.viewmodel.bookmark.BookmarkViewModel
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.theme.Body1
import team.returm.jobisdesignsystem.theme.Body3
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun BookmarkedScreen(
    navController: NavController,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
) {

    val state by bookmarkViewModel.container.stateFlow.collectAsState()

    val bookmarks = remember {
        mutableStateListOf<BookmarkedRecruitmentEntity>()
    }

    LaunchedEffect(Unit) {
        bookmarkViewModel.container.stateFlow.collect {
            bookmarks.clear()
            bookmarks.addAll(it.bookmarkedRecruitments)
        }
    }

    LaunchedEffect(Unit) {
        bookmarkViewModel.fetchBookmarkedRecruitments()
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
        Box {
            BookmarkedRecruitments(
                bookmarks = bookmarks,
                navController = navController,
            ) {
                bookmarkViewModel.bookmarkRecruitment(recruitmentId = it.recruitmentId)
                bookmarks.remove(it)
            }
            if(!state.bookmarkExists) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 72.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Body1(text = stringResource(id = R.string.bookmarked_not_exist))
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.jobisClickable {
                            navController.navigate(JobisRoute.Recruitments)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Caption(
                            text = stringResource(id = R.string.bookmarked_get_recruitments),
                            color = JobisColor.Gray600,
                        )
                        JobisImage(
                            modifier = Modifier
                                .size(14.dp)
                                .padding(top = 2.dp),
                            drawable = JobisIcon.RightArrow,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BookmarkedRecruitments(
    bookmarks: MutableList<BookmarkedRecruitmentEntity>,
    navController: NavController,
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
                        ) {
                            navController.navigate("RecruitmentDetails/${item.recruitmentId}")
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart),
                )
            }
        )
    }
}

@Composable
private fun BookmarkedRecruitment(
    companyName: String,
    createdAt: String,
    onClickRecruitment: () -> Unit,
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
            .padding(16.dp)
            .jobisClickable {
                onClickRecruitment()
            },
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
                color = JobisColor.Red,
                shape = RoundedCornerShape(12.dp),
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