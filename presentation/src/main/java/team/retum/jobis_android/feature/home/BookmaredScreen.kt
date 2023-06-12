package team.retum.jobis_android.feature.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
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
import kotlin.math.roundToInt

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
        BookmarkedRecruitments(bookmarks = bookmarks) { recruitmentId, index ->
//            bookmarkViewModel.bookmarkRecruitment(recruitmentId)
//            bookmarks.removeAt(index)
        }
    }
}

@Composable
private fun BookmarkedRecruitments(
    bookmarks: List<BookmarkedRecruitmentEntity>,
    onSwipeItem: (Long, Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 30.dp)
    ) {
        items(bookmarks) {

            val recruitmentId = it.recruitmentId

            BookmarkedRecruitment(
                companyName = it.companyName,
                recruitmentId = recruitmentId,
                createdAt = it.createdAt,
                onSwipeItem = onSwipeItem,
                index = bookmarks.indexOf(it),
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BookmarkedRecruitment(
    companyName: String,
    recruitmentId: Long,
    createdAt: String,
    onSwipeItem: (Long, Int) -> Unit,
    index: Int,
) {

    val swipeableState = rememberSwipeableState(0)

    val point = LocalDensity.current.run { LocalConfiguration.current.screenWidthDp.dp.toPx() }

    val context = LocalContext.current

    Box {
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
        Row(
            modifier = Modifier
                .offset {
                    val offset = swipeableState.offset.value.roundToInt()
                    if(offset == -350) onSwipeItem(recruitmentId, index)
                    IntOffset(offset, 0)
                }
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
                .swipeable(
                    state = swipeableState,
                    anchors = mapOf(
                        0f to 0,
                        -dpToPx(
                            context = context,
                            dpValue = 100f,
                        ) to 10,
                    ),
                    thresholds = { _, _ ->
                        FractionalThreshold(0.7f)
                    },
                    orientation = Orientation.Horizontal
                ),
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
}

private fun dpToPx(
    context: Context,
    dpValue: Float,
): Float = dpValue * context.resources.displayMetrics.density