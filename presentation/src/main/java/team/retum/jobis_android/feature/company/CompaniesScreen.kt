package team.retum.jobis_android.feature.company

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import team.retum.domain.entity.company.CompanyEntity
import team.retum.jobis_android.feature.home.ApplyCompaniesItemShape
import team.retum.jobis_android.feature.recruitment.Filter
import team.retum.jobis_android.feature.recruitment.Header
import team.retum.jobis_android.viewmodel.company.CompanyViewModel
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
fun CompaniesScreen(
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
                top = 48.dp,
                start = 24.dp,
                end = 24.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(text = stringResource(id = R.string.company_list_search_company))
        Spacer(modifier = Modifier.height(12.dp))
        Filter(onFilterClicked = {})
        Companies(
            companies = state.companies,
            navController = navController,
        )
    }
}

@Composable
private fun Companies(
    companies: List<CompanyEntity>,
    navController: NavController,
) {
    LazyColumn(contentPadding = PaddingValues(vertical = 20.dp)) {
        items(companies) { item ->
            Company(
                name = item.name,
                logoUrl = item.logoUrl,
                take = item.take,
                hasRecruitment = item.hasRecruitment,
            ) {
                navController.navigate("CompanyDetails/${item.id}")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun Company(
    name: String,
    logoUrl: String,
    take: Float,
    hasRecruitment: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = ApplyCompaniesItemShape)
            .background(color = JobisColor.Gray100)
            .jobisClickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 8.dp,
                    end = 20.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier.size(80.dp),
                model = logoUrl,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Body2(text = name)
                Spacer(modifier = Modifier.height(2.dp))
                Caption(
                    text = stringResource(id = R.string.company_list_million, take.toString()),
                    color = JobisColor.Gray600,
                )
                if(hasRecruitment) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End,
                    ) {
                        JobisImage(drawable = R.drawable.ic_recruitment_exists)
                    }
                }
            }
        }
    }
}