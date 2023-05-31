package team.retum.jobis_android.feature.recruitment

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.viewmodel.recruitment.RecruitmentViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.ui.theme.Body1
import team.retum.jobisui.ui.theme.Caption
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.image.JobisImage

@Composable
internal fun RecruitmentDetailsScreen(
    navController: NavController,
    recruitmentId: Long,
    recruitmentViewModel: RecruitmentViewModel = hiltViewModel(),
) {

    val state = recruitmentViewModel.container.stateFlow.collectAsState()

    val companyName = remember {
        mutableStateOf(
            navController.previousBackStackEntry?.arguments?.getString("companyName")!!
        )
    }

    LaunchedEffect(Unit) {
        recruitmentViewModel.setRecruitmentId(
            recruitmentId = recruitmentId,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 28.dp,
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp,
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(
                    state = ScrollState(0),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CompanyInformation(companyName = companyName.value)
            Spacer(modifier = Modifier.height(30.dp))
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = JobisColor.Gray400,
            )
            Spacer(modifier = Modifier.height(20.dp))
            RecruitmentDetails(
                details = recruitmentViewModel.getRecruitmentDetails(),
            )
            Spacer(modifier = Modifier.height(80.dp))
        }
        JobisLargeButton(
            text = stringResource(id = R.string.recruitment_details_do_apply),
            color = JobisButtonColor.MainSolidColor,
            onClick = {},
        )
    }
}

@Composable
private fun CompanyInformation(
    companyName: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JobisImage(
            drawable = R.drawable.ic_get_recruitment
        )
        Spacer(modifier = Modifier.width(16.dp))
        Body1(text = companyName)
    }
    Spacer(modifier = Modifier.height(12.dp))
    JobisLargeButton(
        text = stringResource(id = R.string.recruitment_details_get_company),
        color = JobisButtonColor.MainGrayColor,
    ) {

    }
}

@Composable
private fun RecruitmentDetails(
    details: List<Pair<Int, Any?>>,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        RecruitmentDetail(
            title = stringResource(details[0].first),
            content = details[0].second.toString(),
        )
        Spacer(modifier = Modifier.height(40.dp))
        repeat(details.size) { index ->
            RecruitmentDetail(
                title = stringResource(details[index].first),
                content = (details[index].second
                    ?: stringResource(id = R.string.company_details_null)).toString(),
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

}

@Composable
private fun RecruitmentDetail(
    title: String,
    content: String,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
    ) {
        Caption(
            modifier = Modifier.defaultMinSize(
                minWidth = 68.dp,
            ),
            text = title,
            color = JobisColor.Gray700,
        )
        Spacer(modifier = Modifier.width(24.dp))
        Caption(
            text = content,
            color = JobisColor.Gray900,
        )
    }
}