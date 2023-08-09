package team.retum.jobis_android.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.jobis.jobis_android.R
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.util.compose.animation.bounceClick
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Body3

@Composable
internal fun MenuScreen(
    navController: NavController,
    navHostController: NavHostController,
) {

    val navigateToMyPage = {
        navHostController.navigate(JobisRoute.Navigation.MyPage)
    }

    val navigateToRecruitments = {
        navController.navigate(JobisRoute.Recruitments)
    }

    val navigateToCompanies = {
        navController.navigate(JobisRoute.Companies)
    }

    val navigateToBookmarkedRecruitments = {
        navHostController.navigate(JobisRoute.Navigation.BookmarkRecruitments)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            Header(text = stringResource(id = R.string.menu))
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Body3(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(id = R.string.user),
                color = JobisColor.Gray600,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Menu(
                drawableRes = R.drawable.ic_my_page,
                content = stringResource(id = R.string.bottom_nav_my_page),
                onClick = navigateToMyPage,
            )
            Spacer(modifier = Modifier.height(40.dp))
            Body3(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = stringResource(id = R.string.company),
                color = JobisColor.Gray600,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Menu(
                drawableRes = R.drawable.ic_fetch_recruitments,
                content = stringResource(id = R.string.fetch_recruitments),
                onClick = navigateToRecruitments,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Menu(
                drawableRes = R.drawable.ic_fetch_companies,
                content = stringResource(id = R.string.fetch_companies),
                onClick = navigateToCompanies,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Menu(
                drawableRes = R.drawable.ic_fetch_bookmarked_recruitments,
                content = stringResource(id = R.string.fetch_bookmarked_companies),
                onClick = navigateToBookmarkedRecruitments,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun Menu(
    @DrawableRes drawableRes: Int,
    content: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .bounceClick(onClick = onClick)
            .padding(
                vertical = 8.dp,
                horizontal = 10.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = drawableRes),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Body2(
            text = content,
            color = JobisColor.Gray800,
        )
    }
}