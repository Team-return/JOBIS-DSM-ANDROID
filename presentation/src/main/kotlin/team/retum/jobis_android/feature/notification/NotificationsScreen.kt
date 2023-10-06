package team.retum.jobis_android.feature.notification

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jobis.jobis_android.R
import team.retum.jobis_android.util.compose.component.Header
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.theme.Body3
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun NotificationsScreen(

) {

    // TODO 더미값 제거
    val notifications = listOf(
        Notification(
            id = 0,
            title = "title",
            companyName = "companyName",
            date = "date",
            isOpened = false,
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Header(text = stringResource(id = R.string.notifications_alarm))
        Spacer(modifier = Modifier.height(24.dp))
        Notifications(
            notifications = notifications,
            navigateToNotificationDetails = { },
        )
    }
}

@Composable
private fun Notifications(
    notifications: List<Notification>,
    navigateToNotificationDetails: (Long) -> Unit,
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        items(notifications) {
            Notification(
                id = it.id,
                title = it.title,
                companyName = it.companyName,
                date = it.date,
                onClick = navigateToNotificationDetails,
                isOpened = it.isOpened,
            )
        }
    }
}

// TODO 서버랑 통일
data class Notification(
    val id: Long,
    val title: String,
    val companyName: String,
    val date: String,
    val isOpened: Boolean,
)

// TODO 변수명 서버와 통일
@Composable
private fun Notification(
    id: Long,
    title: String,
    companyName: String,
    date: String,
    onClick: (Long) -> Unit,
    isOpened: Boolean,
) {

    val alpha by animateFloatAsState(
        targetValue = if (isOpened) 0.4f
        else 1f,
        label = "",
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha)
            .jobisClickable(
                rippleEnabled = true,
                onClick = { onClick(id) },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Caption(
                text = companyName,
                color = JobisColor.ToastBlue,
            )
            Body3(text = title)
            Spacer(modifier = Modifier.height(6.dp))
            Caption(
                text = date,
                color = JobisColor.Gray600,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = JobisIcon.RightArrow),
            contentDescription = null,
        )
    }
}