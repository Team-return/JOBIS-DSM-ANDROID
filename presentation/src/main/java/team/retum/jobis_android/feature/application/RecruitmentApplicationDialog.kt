package team.retum.jobis_android.feature.application

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobis.jobis_android.R
import team.retum.jobis_android.feature.recruitment.Header
import team.retum.jobis_android.util.FileUtil
import team.retum.jobis_android.viewmodel.file.FileViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.button.JobisMediumButton
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun RecruitmentApplicationDialog(
    fileViewModel: FileViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        result.data!!.data?.run {
            fileViewModel.addFile(
                FileUtil.toFile(
                    context = context,
                    uri = this,
                ),
            )
        }
    }

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
        SubmitSpace(description = stringResource(id = R.string.add_to_press_file)) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            launcher.launch(intent)
        }
        Spacer(modifier = Modifier.height(18.dp))
        Caption(text = stringResource(id = R.string.url))
        Spacer(modifier = Modifier.height(6.dp))
        SubmitSpace(description = stringResource(id = R.string.add_to_press_url)) {

        }
        Spacer(modifier = Modifier.height(32.dp))
        Box(modifier = Modifier.padding(horizontal = 96.dp)) {
            JobisMediumButton(
                text = stringResource(id = R.string.check),
                color = JobisButtonColor.MainSolidColor,
            ) {
                fileViewModel.uploadFile()
            }
        }
    }
}

@Composable
private fun SubmitSpace(
    description: String,
    onClick: () -> Unit,
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
            .jobisClickable(rippleEnabled = true) {
                onClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        JobisImage(
            drawable = JobisIcon.Upload,
        )
        Caption(text = description)
    }
}