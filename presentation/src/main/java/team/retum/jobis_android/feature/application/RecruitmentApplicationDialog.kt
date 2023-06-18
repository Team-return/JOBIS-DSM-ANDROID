package team.retum.jobis_android.feature.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jobis.jobis_android.R
import team.retum.jobis_android.feature.recruitment.Header
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.theme.Caption

@Composable
internal fun RecruitmentApplicationDialog() {
    Column(
        modifier = Modifier
            .background(JobisColor.Gray100)
            .padding(
                horizontal = 18.dp,
                vertical = 18.dp,
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Header(text = stringResource(id = R.string.do_apply))
        Spacer(modifier = Modifier.height(25.dp))
        Caption(
            text = stringResource(id = R.string.submitted_document, "없음"),
            color = JobisColor.Gray600,
        )
        Spacer(modifier = Modifier.height(14.dp))
        Caption(text = stringResource(id = R.string.attached_file))
        Spacer(modifier = Modifier.height(6.dp))
        SubmitSpace(description = stringResource(id = R.string.add_to_press_file))
        Spacer(modifier = Modifier.height(18.dp))
        Caption(text = stringResource(id = R.string.url))
        Spacer(modifier = Modifier.height(6.dp))
        SubmitSpace(description = stringResource(id = R.string.add_to_press_url))
    }
}

@Composable
private fun SubmitSpace(
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 60.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
            )
            .clip(shape = RoundedCornerShape(16.dp))
            .background(
                color = JobisColor.Gray100,
                shape = RoundedCornerShape(16.dp),
            )
            ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        JobisImage(drawable = JobisIcon.Upload)
        Caption(text = description)
    }
}