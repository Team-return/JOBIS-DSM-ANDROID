package team.retum.jobis_android.feature.recruitment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobis_android.feature.home.ApplyCompaniesItemShape
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.ui.theme.Body2
import team.retum.jobisui.ui.theme.Caption
import team.returm.jobisdesignsystem.image.JobisImage

@Composable
internal fun SearchRecruitmentScreen(
    navController: NavController,
) {

    val tempList = listOf<String>("siefjsef", "seifjseifj")

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
            text = stringResource(id = R.string.search_recruitment_header),
        )
        Spacer(modifier = Modifier.height(40.dp))
        RecruitmentList(
            recruitments = tempList,
        )
    }
}

@Composable
private fun Header(
    text: String,
) {
    Column {
        Body2(
            modifier = Modifier
                .fillMaxWidth()
                .align(
                    alignment = Alignment.Start,
                ),
            text = text,
            color = JobisColor.Gray600,
        )
        Spacer(
            modifier = Modifier.height(10.dp),
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = JobisColor.Gray400,
        )
    }
}

@Composable
private fun RecruitmentList(
    recruitments: List<String>,
) {
    LazyColumn {
        items(
            count = recruitments.size,
        ) { index ->
            Spacer(
                modifier = Modifier.height(16.dp),
            )
            Recruitment(
                image = R.drawable.ic_get_recruitment,
                position = "position",
                companyName = "companyName",
                isBookmarked = false,
            )

            if (index == recruitments.lastIndex) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun Recruitment(
    image: Int,
    position: String,
    companyName: String,
    isBookmarked: Boolean,
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
            ),
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Body2(
                        text = position,
                    )
                    JobisImage(
                        drawable = R.drawable.ic_bookmarked_off,
                    )
                }
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Caption(
                        text = companyName,
                    )
                    JobisImage(
                        drawable = R.drawable.ic_military_support,
                    )
                }
            }
        }
    }
}