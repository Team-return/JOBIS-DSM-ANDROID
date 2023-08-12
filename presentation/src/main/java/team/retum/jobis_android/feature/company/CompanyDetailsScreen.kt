package team.retum.jobis_android.feature.company

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import team.retum.domain.entity.company.CompanyDetailsEntity
import team.retum.domain.entity.review.ReviewEntity
import team.retum.jobis_android.contract.CompanySideEffect
import team.retum.jobis_android.root.navigation.NavigationRoute
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.viewmodel.company.CompanyViewModel
import team.retum.jobis_android.viewmodel.review.ReviewViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.theme.Body1
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Stable
val ReviewItemShape = RoundedCornerShape(size = 14.dp)

@Composable
fun CompanyDetailsScreen(
    companyId: Int,
    hasRecruitment: Boolean,
    getPreviousDestination: () -> String?,
    navigateToRecruitmentDetails: (Long?) -> Unit,
    navigateToReviewDetails: (String) -> Unit,
    companyViewModel: CompanyViewModel = hiltViewModel(),
    reviewViewModel: ReviewViewModel = hiltViewModel(),
) {

    var detailButtonShowed by remember { mutableStateOf(true) }

    val companyState by companyViewModel.container.stateFlow.collectAsState()
    val reviewState by reviewViewModel.container.stateFlow.collectAsState()

    LaunchedEffect(Unit) {
        detailButtonShowed = getPreviousDestination() != NavigationRoute.RecruitmentDetails

        companyViewModel.setCompanyId(
            companyId = companyId,
        )
        companyViewModel.fetchCompanyDetails()

        reviewViewModel.setCompanyId(
            companyId = companyId.toLong(),
        )

        reviewViewModel.fetchReviews()

        companyViewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is CompanySideEffect.NotFoundCompany -> {

                }

                else -> {

                }
            }
        }

        reviewViewModel.container.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                else -> {

                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 48.dp,
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp,
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = ScrollState(0),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header(text = stringResource(id = R.string.company_list_search_company))
            Spacer(modifier = Modifier.height(16.dp))
            CompanyDetails(details = companyState.companyDetails)
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = JobisColor.Gray400,
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (reviewState.reviews.isNotEmpty()) {
                Body2(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.Start),
                    text = stringResource(id = R.string.company_details_review_interview),
                    color = JobisColor.Gray700,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Reviews(
                    reviews = reviewState.reviews,
                    navigateToReviewDetails = navigateToReviewDetails,
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
        if (detailButtonShowed) {
            JobisLargeButton(
                text = stringResource(id = R.string.company_details_see_recruitents),
                color = JobisButtonColor.MainSolidColor,
                enabled = hasRecruitment,
                onClick = { navigateToRecruitmentDetails(companyState.companyDetails.recruitmentId?.toLong()) }
            )
        }
    }
}

@Composable
private fun CompanyDetails(
    details: CompanyDetailsEntity,
) {

    var showDetails by remember { mutableStateOf(false) }

    val maxLines by animateIntAsState(
        targetValue = if (showDetails) 20
        else 3,
        label = ""
    )

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier.size(80.dp),
                model = details.companyProfileUrl,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Body1(text = details.companyName)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Caption(
            text = details.companyIntroduce,
            color = JobisColor.Gray700,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Caption(
                modifier = Modifier.jobisClickable {
                    showDetails = !showDetails
                },
                text = if (showDetails) stringResource(id = R.string.recruitment_details_show_simply)
                else stringResource(id = R.string.recruitment_details_show_detail),
                color = JobisColor.Gray600,
                overflow = TextOverflow.Ellipsis,
                decoration = TextDecoration.Underline,
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = JobisColor.Gray400,
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_representative_name),
            content = details.representativeName,
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_founded_at),
            content = details.foundedAt,
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_worker_number),
            content = "${details.workerNumber}명",
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_take),
            content = "${details.take.toInt()}억",
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_address1),
            content = details.address1,
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_address2),
            content = details.address2 ?: stringResource(id = R.string.company_details_null),
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_manager1),
            content = details.manager1,
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_phone_number1),
            content = details.phoneNumber1,
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_manager2),
            content = details.manager2 ?: stringResource(id = R.string.company_details_null),
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_phone_number2),
            content = details.phoneNumber2 ?: stringResource(id = R.string.company_details_null),
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_email),
            content = details.email,
        )
        Spacer(modifier = Modifier.height(10.dp))
        CompanyDetail(
            title = stringResource(id = R.string.company_details_fax),
            content = details.fax ?: stringResource(id = R.string.company_details_null),
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun Reviews(
    reviews: List<ReviewEntity>,
    navigateToReviewDetails: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .height(180.dp)
            .verticalScroll(
                state = ScrollState(0),
            ),
    ) {
        repeat(reviews.size) { index ->
            val item = reviews[index]
            Review(
                reviewId = item.reviewId,
                writer = item.writer,
                year = item.year.toString(),
                onClick = { navigateToReviewDetails(item.reviewId) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.5f))
    }
}

@Composable
private fun CompanyDetail(
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


@Composable
private fun Review(
    reviewId: String,
    writer: String,
    year: String,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp)
            .clip(
                shape = ReviewItemShape,
            )
            .jobisClickable(
                rippleEnabled = true,
                onClick = { onClick(reviewId) },
            )
            .border(
                width = 1.dp,
                color = JobisColor.Gray400,
                shape = ReviewItemShape,
            )
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Caption(text = stringResource(id = R.string.reviews_writer, writer))
        Caption(
            text = year,
            color = JobisColor.Gray600,
        )
    }
}