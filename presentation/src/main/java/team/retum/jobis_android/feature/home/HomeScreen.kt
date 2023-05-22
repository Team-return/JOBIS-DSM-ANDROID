package team.retum.jobis_android.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import team.retum.jobis_android.viewmodel.home.HomeViewModel
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.ui.theme.Body1
import team.retum.jobisui.ui.theme.Body2
import team.retum.jobisui.ui.theme.Body3
import team.retum.jobisui.ui.theme.Body4
import team.retum.jobisui.ui.theme.Caption
import team.retum.jobisui.ui.theme.Heading3
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.util.JobisSize

@Stable
val ApplyCompaniesItemShape = RoundedCornerShape(
    size = 10.dp,
)

@Composable
internal fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    var totalStudentCount by remember { mutableStateOf(0) }
    var passCount by remember { mutableStateOf(0) }
    var appliedCount by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    val applyCompanies = remember { mutableStateListOf<ApplyCompaniesEntity>() }


    LaunchedEffect(Unit) {
        homeViewModel.sendEvent(
            event = HomeEvent.FetchUserApplyCompanies,
        )

        homeViewModel.container.stateFlow.collect {
            totalStudentCount = it.totalStudentCont
            passCount = it.passCount
            appliedCount = it.approvedCount
            name = it.name
            applyCompanies.addAll(it.applyCompanies)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RecruitmentStatus(
            totalStudentCount = totalStudentCount,
            passCount = passCount,
            appliedCount = passCount,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.padding(
                horizontal = 24.dp,
            )
        ) {
            UserInformation(
                name = name,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                Caption(
                    text = stringResource(id = R.string.home_apply_status),
                    color = JobisColor.Gray600,
                )
            }
            ApplyCompanies(
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
private fun RecruitmentStatus(
    totalStudentCount: Int,
    passCount: Int,
    appliedCount: Int,
) {

    var employmentRate = 0f
    val passString = "$passCount / $totalStudentCount"
    val appliedString = "$appliedCount / $totalStudentCount"

    if (appliedCount != 0) {
        employmentRate = totalStudentCount.div(appliedCount).toFloat()
    }

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
                horizontal = 32.dp,
            ),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Heading3(
                        text = employmentRate.toString(),
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
                    text = "${stringResource(id = R.string.home_count_success_candidate)} : $passString",
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
                    text = "${stringResource(id = R.string.home_count_apply_candidate)} : $appliedString",
                    color = JobisColor.Gray100,
                )
            }
        }
    }
}

@Composable
private fun UserInformation(
    name: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JobisImage(
            modifier = Modifier.size(40.dp),
            drawable = R.drawable.ic_profile,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Body2(
                text = name,
            )
            Caption(
                text = "소개과",
                color = JobisColor.Gray600,
            )
        }
    }
}

@Composable
private fun ApplyCompanies(
    applyCompanies: List<ApplyCompaniesEntity>,
) {
    val size = if (applyCompanies.size >= 2) 2
    else applyCompanies.size

    if(applyCompanies.isNotEmpty()) {
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
                ApplyCompany(
                    company = applyCompanies[index].companyName,
                    status = applyCompanies[index].status.value,
                )
                if (index == size - 1) {
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    }else {
        ApplyCompany(
            isEmpty = true,
        )
    }
}

@Composable
private fun ApplyCompany(
    company: String? = null,
    status: String? = null,
    isEmpty: Boolean? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(
                shape = ApplyCompaniesItemShape,
            )
            .border(
                width = 2.dp,
                color = JobisColor.Gray400,
                shape = ApplyCompaniesItemShape,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if(isEmpty == false) {
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
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Body3(
                        text = company!!
                    )
                    Body3(
                        text = " $status ",
                        color = JobisColor.LightBlue,
                    )
                }
            }
        }else{
            Caption(
                text = stringResource(id = R.string.home_no_apply_companies),
                color = JobisColor.Gray500,
            )
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