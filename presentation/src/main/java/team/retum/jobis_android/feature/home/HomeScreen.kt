package team.retum.jobis_android.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import team.retum.domain.entity.ApplyCompaniesEntity
import team.retum.jobis_android.contract.HomeEvent
import team.retum.jobis_android.contract.HomeSideEffect
import team.retum.jobis_android.viewmodel.home.HomeViewModel
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.ui.theme.Body1
import team.retum.jobisui.ui.theme.Body3
import team.retum.jobisui.ui.theme.Body4
import team.retum.jobisui.ui.theme.Caption
import team.retum.jobisui.ui.theme.Heading3
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.util.JobisSize

@Composable
internal fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    val applyCompanies = remember { mutableStateListOf<ApplyCompaniesEntity>() }

    LaunchedEffect(Unit) {
        homeViewModel.sendEvent(
            event = HomeEvent.FetchUserApplyCompanies,
        )

        homeViewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.SuccessUserApplyCompanies -> {
                    applyCompanies.addAll(sideEffect.applyCompanies)
                }

                else -> {
                    // TODO 토스트 처리
                }
            }
        }

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        StudentInformation(
            employmentRate = "32.5",
            major = "major",
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (applyCompanies.isNotEmpty()) {
            CompanyApplyHistoryList(
                applyCompanies = applyCompanies,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = JobisColor.Gray200,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier.height(28.dp),
            )
            MenuCardGroup()
        }
    }

}

@Composable
private fun StudentInformation(
    employmentRate: String,
    major: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(98.dp)
            .padding(
                horizontal = 14.dp,
            )
            .shadow(
                elevation = 48.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    bottomStart = 34.dp,
                    bottomEnd = 34.dp,
                ),
            )
            .background(
                brush = Brush.horizontalGradient(listOf(JobisColor.Blue, JobisColor.LightBlue)),
            ),
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 30.dp,
            ),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Heading3(
                        text = employmentRate,
                        color = JobisColor.Gray100,
                    )
                    Body1(
                        modifier = Modifier.padding(
                            start = 4.dp,
                            bottom = 4.dp,
                        ),
                        text = "%",
                        color = JobisColor.Gray100,
                    )
                }
                Caption(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 4.dp,
                        ),
                    text = "${stringResource(id = R.string.home_count_success_candidate)} : 10/65",
                    color = JobisColor.Gray100,
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Body4(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    text = stringResource(id = R.string.home_employment_rate),
                    color = JobisColor.Gray100,
                )
                Caption(
                    text = "${stringResource(id = R.string.home_count_apply_candidate)} : 32/65",
                    color = JobisColor.Gray100,
                )
            }
        }
    }
}

@Composable
private fun CompanyApplyHistoryList(
    applyCompanies: List<ApplyCompaniesEntity>,
) {

    Box(
        modifier = Modifier.padding(
            horizontal = 30.dp,
        )
    ) {

        val size = if (applyCompanies.size >= 2) 2
        else applyCompanies.size

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
            )
        ) {
            items(
                count = size,
            ) { index ->
                if (index == 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                CompanyApplyHistoryItem(
                    company = applyCompanies[index].companyName,
                    status = applyCompanies[index].status.value,
                    date = "",
                )
                if (index == size - 1) {
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    }
}

@Composable
private fun CompanyApplyHistoryItem(
    company: String,
    status: String,
    date: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .shadow(
                shape = RoundedCornerShape(
                    size = 10.dp,
                ),
                elevation = 4.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 10.dp,
                )
            )
            .background(
                color = JobisColor.Gray200,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 18.dp,
                    vertical = 10.dp,
                ),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Body3(
                    text = company
                )
                Caption(
                    text = date,
                    color = JobisColor.Gray600,
                )
            }
            Row {
                Caption(
                    text = stringResource(id = R.string.home_apply_current),
                )
                Caption(
                    text = " $status ",
                    color = JobisColor.LightBlue,
                )
                Caption(
                    text = stringResource(id = R.string.home_apply_status),
                )
            }
        }
    }
}

@Composable
private fun MenuCardGroup() {
    Row(
        modifier = Modifier
            .height(200.dp)
            .padding(
                horizontal = 24.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Card(
            modifier = Modifier.weight(0.6f),
            alignment = Alignment.BottomEnd,
            text = stringResource(id = R.string.home_do_get_recruitment),
            drawable = R.drawable.ic_get_recruitment,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Card(
            modifier = Modifier.weight(0.4f),
            alignment = Alignment.BottomCenter,
            text = stringResource(id = R.string.home_do_get_company),
            drawable = R.drawable.ic_get_company,
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun Card(
    modifier: Modifier = Modifier,
    alignment: Alignment,
    text: String,
    @DrawableRes drawable: Int? = null,
) {
    Box(
        modifier = modifier,
        contentAlignment = alignment,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    shape = JobisSize.Shape.Large,
                    elevation = 8.dp,
                )
                .clip(
                    shape = JobisSize.Shape.Large,
                )
                .background(color = JobisColor.Gray100)
                .padding(
                    horizontal = 22.dp,
                    vertical = 14.dp,
                ),
        ) {
            Body1(
                text = text,
            )
        }
        if (drawable != null) {
            JobisImage(
                drawable = drawable,
            )
        }
    }
}