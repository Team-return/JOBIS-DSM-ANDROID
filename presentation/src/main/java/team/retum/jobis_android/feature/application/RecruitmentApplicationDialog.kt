package team.retum.jobis_android.feature.application

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun RecruitmentApplicationDialog(
    fileViewModel: FileViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val state = fileViewModel.container.stateFlow.collectAsState()

    val files = state.value.files

    val urls = remember { mutableStateListOf<String>() }

    var fileCount by remember { mutableStateOf(0) }
    var urlCount by remember { mutableStateOf(0) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        result.data?.data?.run {
            fileViewModel.setFiles(
                FileUtil.toFile(
                    context = context,
                    uri = this,
                ),
            )
            fileCount += 1
        }
    }

    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(14.dp))
            .verticalScroll(rememberScrollState())
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
        Caption(
            text = stringResource(id = R.string.attached_file),
            color = JobisColor.Gray600,
        )
        Spacer(modifier = Modifier.height(6.dp))
        repeat(fileCount) {
            AttachedFile(
                fileName = files[it].name,
                fileSize = (files[it].length() / 1024).toString(),
            ) {

            }
            Spacer(modifier = Modifier.height(6.dp))
        }
        SubmitSpace(description = stringResource(id = R.string.add_to_press_file)) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            launcher.launch(intent)
        }
        Spacer(modifier = Modifier.height(18.dp))
        Caption(
            text = stringResource(id = R.string.url),
            color = JobisColor.Gray600,
        )
        Spacer(modifier = Modifier.height(6.dp))
        repeat(urlCount) { index ->
            AttachedUrl(
                onValueChanged = {
                    urls[index] = it
                },
                url = urls[index],
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        SubmitSpace(description = stringResource(id = R.string.add_to_press_url)) {
            urlCount += 1
            urls.add("")
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

@Composable
private fun AttachedFile(
    fileName: String,
    fileSize: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
            )
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = JobisColor.Gray100,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                start = 20.dp,
                end = 14.dp,
                top = 8.dp,
                bottom = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            Caption(text = fileName)
            Spacer(modifier = Modifier.width(2.dp))
            Caption(
                text = "$fileSize KB",
                color = JobisColor.Gray700,
            )
        }
        JobisImage(
            modifier = Modifier
                .padding(top = 2.dp)
                .jobisClickable { onClick() },
            drawable = JobisIcon.Close,
        )
    }
}

@Composable
private fun AttachedUrl(
    onValueChanged: (String) -> Unit,
    url: String,
) {
    JobisBoxTextField(
        onValueChanged = onValueChanged,
        value = url,
        hint = stringResource(id = R.string.input_url),
    )
}