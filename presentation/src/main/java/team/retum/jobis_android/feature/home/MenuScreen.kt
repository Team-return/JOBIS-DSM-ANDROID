package team.retum.jobis_android.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.feature.recruitment.Header
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Body3

@Composable
internal fun MenuScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 48.dp,
                start = 24.dp,
                end = 24.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(text = stringResource(id = R.string.menu))
        Spacer(modifier = Modifier.height(36.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Body3(
                text = stringResource(id = R.string.user),
                color = JobisColor.Gray600,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Menu(
                drawableRes = R.drawable.ic_my_page,
                content = stringResource(id = R.string.my_page),
            )
            Spacer(modifier = Modifier.height(40.dp))
            Body3(
                text = stringResource(id = R.string.company),
                color = JobisColor.Gray600,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Menu(
                drawableRes = R.drawable.ic_fetch_recruitments,
                content = stringResource(id = R.string.fetch_recruitments),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Menu(
                drawableRes = R.drawable.ic_fetch_companies,
                content = stringResource(id = R.string.fetch_companies)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Menu(
                drawableRes = R.drawable.ic_fetch_bookmarked_recruitments,
                content = stringResource(id = R.string.fetch_bookmarked_companies),
            )
        }
    }
}

@Composable
private fun Menu(
    @DrawableRes drawableRes: Int,
    content: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JobisImage(drawable = drawableRes)
        Spacer(modifier = Modifier.width(12.dp))
        Body2(
            text = content,
            color = JobisColor.Gray800,
        )
    }
}