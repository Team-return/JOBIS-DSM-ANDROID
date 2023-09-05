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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import team.retum.jobis_android.contract.bugreport.BugSideEffect
import team.retum.jobis_android.contract.file.FileSideEffect
import team.retum.jobis_android.root.LocalAppState
import team.retum.jobis_android.util.FileUtil
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.viewmodel.bugreport.BugViewModel
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
internal fun ReportBugScreen(
    bugViewModel: BugViewModel = hiltViewModel(),
    fileViewModel: FileViewModel = hiltViewModel(),
) {

    val appState = LocalAppState.current

    val bugState by bugViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val fileState by fileViewModel.container.stateFlow.collectAsStateWithLifecycle()

    val successReportBugMessage = stringResource(id = R.string.report_bug_success)
    val fileLargeExceptionMessage = stringResource(id = R.string.recruitment_application_file_too_large)

    LaunchedEffect(Unit) {
        bugViewModel.container.sideEffectFlow.collect {
            when (it) {
                is BugSideEffect.SuccessReportBug -> {
                    appState.showSuccessToast(message = successReportBugMessage)
                }

                is BugSideEffect.Exception -> {
                    appState.showErrorToast(message = it.message)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        fileViewModel.container.sideEffectFlow.collect {
            when (it) {
                is FileSideEffect.SuccessUploadFile -> {
                    bugViewModel.setFileUrls(fileUrls = fileState.urls)
                    bugViewModel.reportBug()
                }

                is FileSideEffect.FileLargeException -> {
                    appState.showErrorToast(fileLargeExceptionMessage)
                }

                is FileSideEffect.Exception -> {
                    appState.showErrorToast(it.message)
                }
            }
        }
    }

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
            bugViewModel.addUri(uri)
        }
    }

    val focusManager = LocalFocusManager.current

    val clearFocus = {
        focusManager.clearFocus()
    }

    val onTitleChanged = { title: String ->
        bugViewModel.setTitle(title)

    }

    val onContentChanged = { content: String ->
        bugViewModel.setContent(content)
    }

    val addScreenshot = {
        if (fileState.files.size <= 5) {
            activityResultLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    val removeScreenshot: (Int) -> Unit = { index: Int ->
        fileViewModel.removeFile(index)
        bugViewModel.removeUri(index)
    }

    val positions = listOf(
        stringResource(id = R.string.bug_report_all),
        stringResource(id = R.string.bug_report_server),
        stringResource(id = R.string.bug_report_ios),
        stringResource(id = R.string.bug_report_android),
        stringResource(id = R.string.bug_report_web),
    )

    val onItemSelected = { index: Int ->
        bugViewModel.setPosition(positions[index])
    }

    val onCompleteButtonClicked = {
        fileViewModel.uploadFile()
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
                    title = bugState.title,
                    onContentChanged = onContentChanged,
                    content = bugState.content,
                    titleError = bugState.titleError,
                    contentError = bugState.contentError,
                )
                Spacer(modifier = Modifier.height(24.dp))
                ScreenShots(
                    uriList = bugState.uris,
                    addScreenshot = addScreenshot,
                    removeScreenshot = removeScreenshot,
                    screenShotCount = fileState.files.size,
                )
                Spacer(modifier = Modifier.weight(1f))
                JobisLargeButton(
                    text = stringResource(id = R.string.complete),
                    color = JobisButtonColor.MainSolidColor,
                    onClick = onCompleteButtonClicked,
                    enabled = bugState.reportBugButtonState,
                )
                Spacer(modifier = Modifier.height(44.dp))
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
                            title = stringResource(id = R.string.bug_report_all),
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