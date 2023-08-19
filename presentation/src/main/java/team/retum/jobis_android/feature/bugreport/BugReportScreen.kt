package team.retum.jobis_android.feature.bugreport

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import team.retum.jobis_android.util.FileUtil
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.viewmodel.bugreport.BugReportViewModel
import team.retum.jobis_android.viewmodel.file.FileViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.button.JobisSmallIconButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.colors.JobisDropDownColor
import team.returm.jobisdesignsystem.dropdown.JobisDropDown
import team.returm.jobisdesignsystem.image.JobisImage
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun BugReportScreen(
    bugReportViewModel: BugReportViewModel = hiltViewModel(),
    fileViewModel: FileViewModel = hiltViewModel(),
) {

    val bugReportState by bugReportViewModel.container.stateFlow.collectAsState()
    val fileState by fileViewModel.container.stateFlow.collectAsState()

    val context = LocalContext.current

    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri: Uri? ->
        if (uri != null) {
            val file = FileUtil.toFile(
                context = context,
                uri = uri,
            )
            fileViewModel.addFile(file)
            bugReportViewModel.addUri(uri)
        }
    }

    val focusManager = LocalFocusManager.current

    val clearFocus = {
        focusManager.clearFocus()
    }

    val onTitleChanged = { title: String ->
        bugReportViewModel.setTitle(title)
    }

    val onContentChanged = { content: String ->
        bugReportViewModel.setContent(content)
    }

    val addScreenshot = {
        if (fileState.files.size <= 5) {
            activityResultLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    val removeScreenshot: (Int) -> Unit = { index: Int ->
        fileViewModel.removeFile(index)
        bugReportViewModel.removeUri(index)
    }

    val positions = listOf(
        stringResource(id = R.string.bug_report_all),
        stringResource(id = R.string.bug_report_server),
        stringResource(id = R.string.bug_report_ios),
        stringResource(id = R.string.bug_report_android),
        stringResource(id = R.string.bug_report_web),
    )

    val onItemSelected = { index: Int ->
        bugReportViewModel.setPosition(positions[index])
    }

    val onCompleteButtonClicked = {
        bugReportViewModel.setFileUrls(fileUrls = fileState.urls)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .jobisClickable(onClick = clearFocus),
    ) {
        Box {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Spacer(modifier = Modifier.height(48.dp))
                Header(text = stringResource(id = R.string.bug_report))
                Spacer(modifier = Modifier.height(14.dp))
                ContentInputs(
                    onTitleChanged = onTitleChanged,
                    title = bugReportState.title,
                    onContentChanged = onContentChanged,
                    content = bugReportState.content,
                    titleError = bugReportState.titleError,
                    contentError = bugReportState.contentError,
                )
                Spacer(modifier = Modifier.height(24.dp))
                ScreenShots(
                    uriList = bugReportState.uris,
                    addScreenshot = addScreenshot,
                    removeScreenshot = removeScreenshot,
                    screenShotCount = fileState.files.size,
                )
                Spacer(modifier = Modifier.weight(1f))
                Column(modifier = Modifier.imePadding()) {
                    JobisLargeButton(
                        text = stringResource(id = R.string.complete),
                        color = JobisButtonColor.MainSolidColor,
                        onClick = onCompleteButtonClicked,
                    )
                    Spacer(modifier = Modifier.height(44.dp))
                }
            }
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(88.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Box(modifier = Modifier.width(116.dp)) {
                        JobisDropDown(
                            color = JobisDropDownColor.MainColor,
                            itemList = positions,
                            onItemSelected = onItemSelected,
                            title = stringResource(id = R.string.bug_report_position),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentInputs(
    onTitleChanged: (String) -> Unit,
    title: String,
    titleError: Boolean,
    onContentChanged: (String) -> Unit,
    content: String,
    contentError: Boolean,
) {
    JobisBoxTextField(
        hint = stringResource(id = R.string.bug_report_title),
        onValueChanged = onTitleChanged,
        value = title,
        fieldText = stringResource(id = R.string.bug_report_title),
        error = titleError,
    )
    Spacer(modifier = Modifier.height(24.dp))
    JobisBoxTextField(
        hint = stringResource(id = R.string.bug_report_content),
        onValueChanged = onContentChanged,
        value = content,
        fieldText = stringResource(id = R.string.bug_report_content),
        error = contentError,
    )
}

@Composable
private fun ScreenShots(
    uriList: List<Uri>,
    addScreenshot: () -> Unit,
    removeScreenshot: (Int) -> Unit,
    screenShotCount: Int,
) {
    Column {
        Caption(
            text = "${stringResource(id = R.string.bug_report_screenshots)} ($screenShotCount/5)",
            color = JobisColor.Gray700,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier.height(150.dp)
        ) {
            if (screenShotCount == 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = JobisColor.Gray400,
                            shape = RoundedCornerShape(16.dp),
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .jobisClickable(
                            rippleEnabled = true,
                            onClick = addScreenshot,
                        )
                        .padding(
                            horizontal = 146.dp,
                            vertical = 46.dp,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    JobisImage(drawable = R.drawable.ic_image)
                }
            }
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                itemsIndexed(uriList) { index, uri ->
                    Box(contentAlignment = Alignment.BottomEnd) {
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                        )
                        Box(
                            modifier = Modifier.padding(
                                bottom = 4.dp,
                                end = 6.dp,
                            ),
                        ) {
                            JobisSmallIconButton(
                                drawable = R.drawable.ic_trash_red,
                                onClick = { removeScreenshot(index) },
                                color = JobisButtonColor.MainShadowColor,
                                shadow = true,
                            )
                        }
                    }
                }
                if (screenShotCount in 1..4) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            JobisSmallIconButton(
                                drawable = R.drawable.ic_add_blue,
                                onClick = addScreenshot,
                                color = JobisButtonColor.MainShadowColor,
                                shadow = true,
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
        }
    }
}