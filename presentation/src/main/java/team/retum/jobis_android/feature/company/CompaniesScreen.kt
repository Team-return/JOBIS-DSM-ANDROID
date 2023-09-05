package team.retum.jobis_android.feature.company

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import team.retum.domain.entity.company.CompanyEntity
import team.retum.jobis_android.feature.home.ApplyCompaniesItemShape
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.viewmodel.company.CompanyViewModel
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
fun CompaniesScreen(
    navigateToCompanyDetails: (Long, Boolean) -> Unit,
    companyViewModel: CompanyViewModel = hiltViewModel(),
) {

    val state by companyViewModel.container.stateFlow.collectAsStateWithLifecycle()

    val onCompanyNameChanged = { name: String ->
        companyViewModel.setCompanyName(name)
    }

    val companies = state.companies

    val searchResultTextAlpha = if (state.name.isNullOrBlank()) 0f else 1f

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
            CompanyInput(
                companyName = state.name,
                onCompanyNameChanged = onCompanyNameChanged,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Caption(
                    modifier = Modifier.alpha(alpha = searchResultTextAlpha),
                    text = stringResource(id = R.string.search_result),
                    color = JobisColor.Gray600,
                )
                Caption(text = state.name ?: "")
            }
            Companies(
                companies = companies,
                navigateToCompanyDetails = navigateToCompanyDetails,
            )
        }
    }
}

@Composable
private fun CompanyInput(
    companyName: String?,
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
            value = companyName ?: "",
            hint = stringResource(id = R.string.search_recruitment_filter_hint),
        )
    }
}

@Composable
private fun Companies(
    companies: List<CompanyEntity>,
    navigateToCompanyDetails: (Long, Boolean) -> Unit,
) {
    LazyColumn(contentPadding = PaddingValues(vertical = 20.dp)) {
        items(companies) { item ->
            Company(
                name = item.name,
                logoUrl = item.logoUrl,
                take = item.take,
                hasRecruitment = item.hasRecruitment,
                onClick = { navigateToCompanyDetails(item.id.toLong(), item.hasRecruitment) },
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
            .jobisClickable(
                onClick = onClick,
                rippleEnabled = true,
            ),
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
                        Image(
                            painter = painterResource(id = R.drawable.ic_recruitment_exists),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}