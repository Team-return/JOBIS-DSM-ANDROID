package team.retum.jobis_android.feature.company

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import team.retum.domain.entity.company.CompanyEntity
import team.retum.jobis_android.feature.home.ApplyCompaniesItemShape
import team.retum.jobis_android.feature.recruitment.Filter
import team.retum.jobis_android.feature.recruitment.Header
import team.retum.jobis_android.viewmodel.company.CompanyViewModel
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.util.jobisClickable
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Caption

@Composable
fun CompanyListScreen(
    navController: NavController,
    companyViewModel: CompanyViewModel = hiltViewModel(),
) {

    val state = companyViewModel.container.stateFlow.collectAsState().value

    LaunchedEffect(Unit) {
        companyViewModel.fetchCompanies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 28.dp,
                start = 24.dp,
                end = 24.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(
            text = stringResource(id = R.string.company_list_search_company),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Filter(
            onFilterClicked = {},
        )
        CompanyList(
            companies = state.companies,
            navController = navController,
        )
    }
}

@Composable
private fun CompanyList(
    companies: List<CompanyEntity>,
    navController: NavController,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 20.dp,
        ),
    ) {
        itemsIndexed(
            items = companies,
        ) { _, item ->
            Company(
                name = item.name,
                logoUrl = item.logoUrl,
                take = item.take,
            ) {
                navController.navigate("CompanyDetails/${item.id}")
            }
            Spacer(
                modifier = Modifier.height(16.dp),
            )
        }
    }
}

@Composable
private fun Company(
    name: String,
    logoUrl: String,
    take: Float,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                shape = ApplyCompaniesItemShape,
                elevation = 8.dp,
            )
            .clip(
                shape = ApplyCompaniesItemShape,
            )
            .background(
                color = JobisColor.Gray100,
            )
            .jobisClickable(
                rippleEnabled = false,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                onClick()
            },
    ) {
        Row(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 20.dp,
                top = 8.dp,
                bottom = 8.dp,
            )
        ) {
            JobisImage(
                modifier = Modifier.size(80.dp),
                drawable = R.drawable.ic_get_recruitment,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.padding(
                    vertical = 12.dp,
                )
            ) {
                Body2(
                    text = name,
                )
                Spacer(
                    modifier = Modifier.height(2.dp),
                )
                Caption(
                    text = stringResource(id = R.string.company_list_million, take.toString()),
                    color = JobisColor.Gray600,
                )
            }
        }
    }
}