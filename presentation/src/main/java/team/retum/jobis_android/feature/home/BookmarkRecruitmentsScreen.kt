package team.retum.jobis_android.feature.home

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobis.jobis_android.R
import team.retum.domain.entity.bookmark.BookmarkedRecruitmentEntity
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.util.compose.vibrate
import team.retum.jobis_android.viewmodel.bookmark.BookmarkViewModel
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.theme.Body1
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun BookmarkRecruitmentsScreen(
    navigateToRecruitmentDetails: (Long) -> Unit,
    navigateToRecruitments: () -> Unit,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val state by bookmarkViewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        bookmarkViewModel.fetchBookmarkedRecruitments()
    }

    val bookmarks = remember {
        mutableStateListOf<BookmarkedRecruitmentEntity>()
    }

    val removeRecruitments = { it: BookmarkedRecruitmentEntity ->
        bookmarkViewModel.bookmarkRecruitment(recruitmentId = it.recruitmentId)
        bookmarks.remove(it)
        vibrate(context = context)
    }

    LaunchedEffect(Unit) {
        bookmarkViewModel.container.stateFlow.collect {
            bookmarks.clear()
            bookmarks.addAll(it.bookmarkedRecruitments)
        }
    }

    val bookmarkNotExistTextAlpha by animateFloatAsState(
        targetValue = if (!state.bookmarkExists) 1f
        else 0f,
        label = "",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Header(text = stringResource(id = R.string.bookmarked_recruitments))
        Box {
            BookmarkedRecruitments(
                context = context,
                bookmarks = bookmarks,
                removeItem = removeRecruitments,
                navigateToRecruitmentDetails = navigateToRecruitmentDetails,
            )
            BookmarkNotExistText(
                navigateToRecruitments = navigateToRecruitments,
                bookmarkNotExistTextAlpha = bookmarkNotExistTextAlpha,
            )
        }
    }
}

@Composable
private fun BookmarkNotExistText(
    navigateToRecruitments: () -> Unit,
    bookmarkNotExistTextAlpha: Float,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 72.dp)
            .alpha(bookmarkNotExistTextAlpha),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Body1(text = stringResource(id = R.string.bookmarked_not_exist))
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.jobisClickable(onClick = navigateToRecruitments),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Caption(
                text = stringResource(id = R.string.bookmarked_get_recruitments),
                color = JobisColor.Gray600,
            )
            Image(
                modifier = Modifier
                    .size(14.dp)
                    .padding(top = 2.dp),
                painter = painterResource(JobisIcon.RightArrow),
                contentDescription = null,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BookmarkedRecruitments(
    context: Context,
    bookmarks: MutableList<BookmarkedRecruitmentEntity>,
    removeItem: (BookmarkedRecruitmentEntity) -> Unit,
    navigateToRecruitmentDetails: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 30.dp)
    ) {
        items(items = bookmarks, key = { it.recruitmentId }, itemContent = { item ->
            val dismissState = rememberDismissState(confirmStateChange = {
                when (it) {
                    DismissValue.Default -> false
                    DismissValue.DismissedToStart -> {
                        removeItem(item)
                        true
                    }

                    DismissValue.DismissedToEnd -> {
                        navigateToRecruitmentDetails(item.recruitmentId)
                        vibrate(context = context)
                        false
                    }
                }
            })
            SwipeToDismiss(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(12.dp),
                    )
                    .jobisClickable(
                        onClick = { navigateToRecruitmentDetails(item.recruitmentId) },
                        rippleEnabled = true,
                    )
                    .clip(RoundedCornerShape(12.dp)),
                state = dismissState,
                background = {

                    val currentDismissState = dismissState.targetValue

                    val backgroundColor by animateColorAsState(
                        targetValue = when (currentDismissState) {
                            DismissValue.DismissedToStart -> JobisColor.Red
                            DismissValue.DismissedToEnd -> JobisColor.Green
                            else -> JobisColor.Gray500
                        }, animationSpec = tween(durationMillis = 200), label = ""
                    )

                    val dismissStartText = when (currentDismissState) {
                        DismissValue.DismissedToStart -> ""
                        DismissValue.DismissedToEnd -> stringResource(id = R.string.bookmark_recruitment_details)
                        else -> stringResource(id = R.string.cancel)
                    }

                    val dismissEndText = when (currentDismissState) {
                        DismissValue.DismissedToStart -> stringResource(id = R.string.bookmark_remove)
                        DismissValue.DismissedToEnd -> ""
                        else -> stringResource(id = R.string.cancel)
                    }

                    val dismissTextColor by animateColorAsState(
                        targetValue = when (currentDismissState) {
                            DismissValue.DismissedToStart -> JobisColor.Gray100
                            DismissValue.DismissedToEnd -> JobisColor.Gray100
                            else -> JobisColor.Gray900
                        },
                        label = ""
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(backgroundColor)
                            .padding(horizontal = 26.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Caption(
                            text = dismissStartText,
                            color = dismissTextColor,
                        )
                        Caption(
                            text = dismissEndText,
                            color = dismissTextColor,
                        )
                    }
                },
                dismissThresholds = { FractionalThreshold(0.25f) },
            ) {
                BookmarkedRecruitment(
                    companyName = item.companyName,
                    createdAt = item.createdAt,
                )
            }
        })
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
            .clip(shape = RoundedCornerShape(12.dp))
            .background(
                color = JobisColor.Gray100,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Body4(text = companyName)
        Caption(
            text = stringResource(id = R.string.bookmarked_date, createdAt.split('T')[0]),
            color = JobisColor.Gray600,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}