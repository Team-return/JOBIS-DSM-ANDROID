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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobis.jobis_android.R
import team.retum.jobisui.colors.JobisColor
import team.retum.jobisui.ui.theme.Body1
import team.retum.jobisui.ui.theme.Body4
import team.retum.jobisui.ui.theme.Caption
import team.retum.jobisui.ui.theme.Heading3
import team.retum.jobisui.ui.theme.Heading6
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.util.JobisSize

@Composable
fun HomeScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        StudentInformation(
            employmentRate = "32.5",
            major = "major",
        )
        Spacer(modifier = Modifier.height(28.dp))
        EmptyComponent()
        Spacer(modifier = Modifier.height(24.dp))
        MenuCardGroup()
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
            .shadow(
                elevation = 48.dp,
            )
            .padding(
                horizontal = 14.dp,
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
private fun EmptyComponent() {

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
            Heading6(
                text = text,
                color = JobisColor.Blue,
            )
        }
        if (drawable != null) {
            JobisImage(
                drawable = drawable,
            )
        }
    }
}

@Composable
private fun MenuCardGroup() {
    Row(
        modifier = Modifier
            .height(170.dp)
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