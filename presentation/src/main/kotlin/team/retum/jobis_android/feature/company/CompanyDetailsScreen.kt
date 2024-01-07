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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
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
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.compose.collectAsState
import team.retum.domain.entity.company.CompanyDetailsEntity
import team.retum.domain.entity.review.ReviewEntity
import team.retum.jobis_android.feature.recruitment.CompanyInformation
import team.retum.jobis_android.feature.recruitment.Detail
import team.retum.jobis_android.feature.review.ReviewViewModel
import team.retum.jobis_android.navigation.MainDestinations
import team.retum.jobis_android.navigation.NavigationProperties
import team.retum.jobis_android.util.compose.animation.skeleton
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Stable
val ReviewItemShape = RoundedCornerShape(size = 14.dp)

@Composable
fun CompanyDetailsScreen(
    companyId: Long,
    getPreviousDestination: () -> String?,
    navigateToRecruitmentDetails: (Long) -> Unit,
    navigateToReviewDetails: (String) -> Unit,
    putString: (key: String, value: String) -> Unit,
    companyViewModel: CompanyViewModel = hiltViewModel(),
    reviewViewModel: ReviewViewModel = hiltViewModel(),
) {
    var detailButtonVisibility by remember { mutableStateOf(true) }
    val companyState by companyViewModel.collectAsState()
    val reviewState by reviewViewModel.collectAsState()

    LaunchedEffect(Unit) {
        detailButtonVisibility =
            getPreviousDestination()?.getNavigationRoute() != MainDestinations.RecruitmentDetails.getNavigationRoute()

        with(companyViewModel) {
            setCompanyId(companyId)
            fetchCompanyDetails()
        }

        with(reviewViewModel) {
            setCompanyId(companyId)
            fetchReviews()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = ScrollState(0)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))
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
                    putString = putString,
                )
            }
            Spacer(modifier = Modifier.height(104.dp))
        }
        if (detailButtonVisibility) {
            Column {
                JobisLargeButton(
                    text = stringResource(id = R.string.company_details_see_recruitments),
                    color = JobisButtonColor.MainSolidColor,
                    enabled = companyState.companyDetails.recruitmentId != null,
                    onClick = {
                        navigateToRecruitmentDetails(
                            companyState.companyDetails.recruitmentId ?: 0,
                        )
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun CompanyDetails(
    details: CompanyDetailsEntity,
) {
    var showDetails by remember { mutableStateOf(false) }

    val maxLines by animateIntAsState(
        targetValue = if (showDetails) {
            100
        } else {
            3
        },
        label = "",
    )

    with(details) {
        Column {
            CompanyInformation(
                companyProfileUrl = companyProfileUrl,
                companyName = companyName,
            )
            Caption(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 12.dp,
                        bottom = 14.dp,
                    )
                    .skeleton(companyIntroduce.isBlank()),
                text = companyIntroduce,
                color = JobisColor.Gray700,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
            )
            Caption(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .jobisClickable {
                        showDetails = !showDetails
                    },
                text = if (showDetails) {
                    stringResource(id = R.string.recruitment_details_show_simply)
                } else {
                    stringResource(id = R.string.recruitment_details_show_detail)
                },
                color = JobisColor.Gray600,
                overflow = TextOverflow.Ellipsis,
                decoration = TextDecoration.Underline,
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 32.dp,
                        bottom = 10.dp,
                    ),
                color = JobisColor.Gray400,
            )
            Detail(
                title = stringResource(id = R.string.company_details_representative_name),
                content = representativeName,
            )
            Detail(
                title = stringResource(id = R.string.company_details_founded_at),
                content = foundedAt,
            )
            Detail(
                title = stringResource(id = R.string.company_details_worker_number),
                content = if (workerNumber == 0L) {
                    ""
                } else {
                    "${workerNumber}명"
                },
            )
            Detail(
                title = stringResource(id = R.string.company_details_take),
                content = if (take == 0f) {
                    ""
                } else {
                    "${take.toInt()}억"
                },
            )
            Detail(
                title = stringResource(id = R.string.company_details_address1),
                content = mainAddress,
            )
            if (!subAddress.isNullOrBlank()) {
                Detail(
                    title = stringResource(id = R.string.company_details_address2),
                    content = subAddress ?: "",
                )
            }
            Detail(
                title = stringResource(id = R.string.company_details_manager1),
                content = managerName,
            )
            Detail(
                title = stringResource(id = R.string.company_details_phone_number1),
                content = managerPhoneNo.toPhoneNumber(),
            )
            if (!subManagerName.isNullOrBlank()) {
                Detail(
                    title = stringResource(id = R.string.company_details_manager2),
                    content = subManagerName ?: "",
                )
            }
            if (!subManagerPhoneNo.isNullOrBlank()) {
                Detail(
                    title = stringResource(id = R.string.company_details_phone_number2),
                    content = subManagerPhoneNo ?: "",
                )
            }
            Detail(
                title = stringResource(id = R.string.company_details_email),
                content = email,
            )
            fax?.run {
                Detail(
                    title = stringResource(id = R.string.company_details_fax),
                    content = this,
                )
            }
        }
    }
}

@Composable
private fun Reviews(
    reviews: List<ReviewEntity>,
    navigateToReviewDetails: (String) -> Unit,
    putString: (key: String, value: String) -> Unit,
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
                onClick = {
                    putString(NavigationProperties.WRITER, item.writer)
                    navigateToReviewDetails(item.reviewId)
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.5f))
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

internal fun String.getNavigationRoute() = this.split("/").first()

private fun String?.toPhoneNumber() = StringBuilder().apply {
    repeat(this@toPhoneNumber?.length ?: 0) {
        when (it) {
            3 -> append("-")
            7 -> append("-")
        }
        append(this@toPhoneNumber?.get(it))
    }
}.toString()
