package team.retum.jobis_android.feature.company

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import team.retum.domain.entity.company.CompanyEntity
import team.retum.jobis_android.feature.home.ApplyCompaniesItemShape
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.viewmodel.company.CompanyViewModel
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Body1
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
fun CompaniesScreen(
    navController: NavController,
    companyViewModel: CompanyViewModel = hiltViewModel(),
) {

    val state = companyViewModel.container.stateFlow.collectAsState().value

    val onCompanyNameChanged = { name: String ->
        companyViewModel.setCompanyName(name)
    }

    val navigateToCompanyDetails = { id: Int, hasRecruitment: Boolean ->
        navController.navigate("CompanyDetails/${id}/${hasRecruitment}")
    }

    LaunchedEffect(Unit) {
        companyViewModel.fetchCompanies()
    }

    val lazyListState = rememberLazyListState()

    val lastIndex = remember {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        }
    }

    val companies = state.companies

    var page by remember { mutableStateOf(1) }

    LaunchedEffect(lastIndex.value) {
        val size = state.companies.size
        if (size - 1 == lastIndex.value && size > 5) {
            page += 1
            companyViewModel.setPage(page + 1)
            companyViewModel.fetchCompanies()
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Header(text = stringResource(id = R.string.company_list_search_company))
            Spacer(modifier = Modifier.height(12.dp))
            if (companies.isNotEmpty()) {
                CompanyInput(
                    companyName = state.name ?: "",
                    onCompanyNameChanged = onCompanyNameChanged,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier.alpha(if (state.name != null) 1f else 0f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (state.name?.isNotBlank() == true) {
                        Caption(
                            text = stringResource(id = R.string.search_result),
                            color = JobisColor.Gray600,
                        )
                        Caption(text = " ${state.name}")
                    }
                }
            }
            Companies(
                companies = companies,
                lazyListState = lazyListState,
                navigateToCompanyDetails = navigateToCompanyDetails,
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Body1(text = stringResource(id = R.string.companies_not_exits))
        }
    }
}

@Composable
private fun CompanyInput(
    companyName: String,
    onCompanyNameChanged: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            onValueChanged = onCompanyNameChanged,
            value = companyName,
            hint = stringResource(id = R.string.search_recruitment_filter_hint),
        )
    }
}

@Composable
private fun Companies(
    companies: List<CompanyEntity>,
    lazyListState: LazyListState,
    navigateToCompanyDetails: (Int, Boolean) -> Unit,
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 20.dp),
    ) {
        items(companies) { item ->
            Company(
                name = item.name,
                logoUrl = item.logoUrl,
                take = item.take,
                hasRecruitment = item.hasRecruitment,
                onClick = { navigateToCompanyDetails(item.id, item.hasRecruitment) },
            )
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
            .shadow(
                elevation = 8.dp,
                shape = ApplyCompaniesItemShape,
            )
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
                if (hasRecruitment) {
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