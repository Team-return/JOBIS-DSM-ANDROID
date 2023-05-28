package team.retum.jobis_android.feature.company

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.CompanySideEffect
import team.retum.jobis_android.feature.recruitment.Header
import team.retum.jobis_android.viewmodel.company.CompanyViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.ui.theme.Body1
import team.retum.jobisui.ui.theme.Body2
import team.retum.jobisui.ui.theme.Caption
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.image.JobisImage

@Composable
fun CompanyDetailsScreen(
    navController: NavController,
    companyId: Int,
    companyViewModel: CompanyViewModel = hiltViewModel(),
) {

    val state = companyViewModel.container.stateFlow.collectAsState().value

    LaunchedEffect(Unit) {
        companyViewModel.setCompanyId(
            companyId = companyId,
        )
        companyViewModel.fetchCompanyDetails()

        companyViewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is CompanySideEffect.NotFoundCompany -> {

                }

                else -> {

                }
            }
        }
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header(
                text = stringResource(id = R.string.company_list_search_company),
            )
            Spacer(modifier = Modifier.height(30.dp))
            CompanyDetails(
                companyProfileUrl = state.companyDetails.companyProfileUrl,
                companyName = state.companyDetails.companyName,
                companyIntroduce = state.companyDetails.companyIntroduce,
                companyDetails = companyViewModel.getCompanyDetails()
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = JobisColor.Gray400,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Body2(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.Start),
                text = stringResource(id = R.string.company_details_review_interview),
                color = JobisColor.Gray700,
            )
        }
        JobisLargeButton(
            text = stringResource(id = R.string.company_details_see_recruitents),
            color = JobisButtonColor.MainSolidColor,
            onClick = {},
        )
    }
}

@Composable
private fun CompanyDetails(
    companyProfileUrl: String,
    companyName: String,
    companyIntroduce: String,
    companyDetails: List<Pair<Int, String?>>,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            JobisImage(
                modifier = Modifier.size(80.dp),
                drawable = R.drawable.ic_get_company,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Body1(text = companyName)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Caption(
            text = companyIntroduce,
            color = JobisColor.Gray700,
        )
        Spacer(modifier = Modifier.height(32.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = JobisColor.Gray400,
        )
        Spacer(modifier = Modifier.height(20.dp))
        repeat(companyDetails.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 10.dp,
                    ),
                verticalAlignment = Alignment.Top,
            ) {
                Caption(
                    modifier = Modifier.defaultMinSize(
                        minWidth = 52.dp,
                    ),
                    text = stringResource(companyDetails[index].first),
                    color = JobisColor.Gray700,
                )
                Spacer(modifier = Modifier.width(24.dp))
                Caption(
                    text = companyDetails[index].second
                        ?: stringResource(id = R.string.company_details_null),
                    color = JobisColor.Gray900,
                )
            }
        }
    }
}