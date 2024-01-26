package team.retum.jobis_android.feature.company

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import kotlinx.coroutines.launch
import team.retum.domain.entity.company.CompanyEntity
import team.retum.jobis_android.feature.main.home.ApplyCompaniesItemShape
import team.retum.jobis_android.util.compose.animation.skeleton
import team.retum.jobis_android.util.compose.component.Header
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.colors.JobisTextFieldColor
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.Animated
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun CompaniesScreen(
    navigateToCompanyDetails: (Long) -> Unit,
    companiesScreenViewModel: CompaniesScreenViewModel = hiltViewModel(),
) {
    val searchResultTextAlpha = if (companiesScreenViewModel.name.isNullOrBlank()) 0f else 1f
    val lazyListState = rememberLazyListState()
    val name = companiesScreenViewModel.name
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        with(companiesScreenViewModel) {
            snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }.callNextPageByPosition()
            fetchCompanies()
            fetchCompanyCount()
            observeName()
        }
    }

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
            companyName = name,
            onCompanyNameChanged = {
                companiesScreenViewModel.setName(it)
                coroutineScope.launch {
                    lazyListState.scrollToItem(
                        index = 0,
                        scrollOffset = 0,
                    )
                }
            },
        )
        Animated(!name.isNullOrBlank()) {
            Caption(
                modifier = Modifier.alpha(alpha = searchResultTextAlpha),
                text = stringResource(id = R.string.search_result),
                color = JobisColor.Gray600,
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
                Caption(text = name ?: "")
            }
        }
        Companies(
            lazyListState = lazyListState,
            companies = companiesScreenViewModel.companies,
            navigateToCompanyDetails = {
                if (it != 0L) {
                    navigateToCompanyDetails(it)
                }
            },
        )
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
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JobisBoxTextField(
            color = JobisTextFieldColor.MainColor,
            onValueChanged = onCompanyNameChanged,
            value = companyName ?: "",
            hint = stringResource(id = R.string.recruitments_filter_hint),
        )
    }
}

@Composable
private fun Companies(
    lazyListState: LazyListState,
    companies: SnapshotStateList<CompanyEntity>,
    navigateToCompanyDetails: (companyId: Long) -> Unit,
) {
    if (companies.isNotEmpty()) {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(vertical = 20.dp),
        ) {
            items(companies) { item ->
                Company(
                    companyId = item.id,
                    name = item.name,
                    logoUrl = item.logoUrl,
                    take = item.take,
                    hasRecruitment = item.hasRecruitment,
                    onClick = { navigateToCompanyDetails(item.id) },
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun Company(
    companyId: Long,
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
                elevation = 4.dp,
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
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .skeleton(
                        show = logoUrl.isBlank(),
                        shape = RoundedCornerShape(16.dp),
                    ),
                model = logoUrl,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                Body2(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 92.dp)
                        .skeleton(name.isBlank()),
                    text = name,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Caption(
                    modifier = Modifier
                        .defaultMinSize(40.dp)
                        .skeleton(take == 0f),
                    text = if (take != 0f) {
                        stringResource(
                            id = R.string.company_list_million,
                            take.toString(),
                        )
                    } else {
                        ""
                    },
                    color = JobisColor.Gray600,
                )
                if (hasRecruitment && companyId != 0L) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_recruitment_exists),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        alignment = Alignment.CenterEnd,
                    )
                }
            }
        }
    }
}
