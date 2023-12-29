package team.retum.jobis_android.feature.bugreport

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.compose.collectSideEffect
import team.retum.domain.enums.DevelopmentArea
import team.retum.jobis_android.LocalAppState
import team.retum.jobis_android.feature.common.FileSideEffect
import team.retum.jobis_android.feature.common.FileViewModel
import team.retum.jobis_android.util.FileUtil
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisLargeButton
import team.returm.jobisdesignsystem.button.JobisSmallIconButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.colors.JobisDropDownColor
import team.returm.jobisdesignsystem.dropdown.JobisDropDown
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun ReportBugScreen(
    bugViewModel: BugViewModel = hiltViewModel(),
    fileViewModel: FileViewModel = hiltViewModel(),
    navigatePopBackStack: () -> Unit,
) {
    val appState = LocalAppState.current
    val bugState by bugViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    fileViewModel.collectSideEffect {
        when (it) {
            is FileSideEffect.Success -> {
                bugViewModel.reportBug(fileViewModel.filePaths)
            }

            else -> {}
        }
    }

    bugViewModel.collectSideEffect {
        when (it) {
            is BugSideEffect.SuccessReportBug -> {
                fileViewModel.resetFiles()
                appState.showSuccessToast(message = context.getString(R.string.report_bug_success))
                navigatePopBackStack()
            }

            is BugSideEffect.Exception -> {
                appState.showErrorToast(message = it.message)
            }
        }
    }

    val activityResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { result ->
            result?.run {
                fileViewModel.files.add(
                    FileUtil.toFile(
                        context = context,
                        uri = this,
                    ),
                )
                bugViewModel.imageUris.add(this)
            }
        }

    val positions = listOf(
        DevelopmentArea.ALL.value,
        DevelopmentArea.SERVER.value,
        DevelopmentArea.IOS.value,
        DevelopmentArea.ANDROID.value,
        DevelopmentArea.WEB.value,
    )

    val onItemSelected: (Int) -> Unit = { index: Int ->
        bugViewModel.setPosition(positions[index])
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .jobisClickable(onClick = focusManager::clearFocus),
    ) {
        Column(
            modifier = Modifier.padding(
                top = 48.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 44.dp,
            ),
        ) {
            Header(text = stringResource(id = R.string.bug_report))
            ContentInputs(
                onTitleChanged = bugViewModel::setTitle,
                title = bugState.title,
                onContentChanged = bugViewModel::setContent,
                content = bugState.content,
                titleError = bugState.titleError,
                contentError = bugState.contentError,
            )
            ScreenShots(
                uris = bugViewModel.imageUris,
                addScreenshot = {
                    activityResultLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly,
                        ),
                    )
                },
                removeScreenshot = { index ->
                    fileViewModel.files.removeAt(index)
                    bugViewModel.imageUris.removeAt(index)
                },
            )
            JobisLargeButton(
                text = stringResource(id = R.string.complete),
                color = JobisButtonColor.MainSolidColor,
                onClick = { fileViewModel.createPresignedUrl()/*bugViewModel.reportBug(fileViewModel.filePaths)*/ },
                enabled = bugState.reportBugButtonState,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 88.dp,
                    start = 16.dp,
                    end = 16.dp,
                ),
            horizontalArrangement = Arrangement.End,
        ) {
            Box(modifier = Modifier.width(116.dp)) {
                JobisDropDown(
                    color = JobisDropDownColor.MainColor,
                    itemList = positions,
                    onItemSelected = onItemSelected,
                    title = DevelopmentArea.ALL.value,
                )
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
    Column(
        modifier = Modifier.padding(top = 14.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        JobisBoxTextField(
            hint = stringResource(id = R.string.bug_report_title),
            onValueChanged = onTitleChanged,
            value = title,
            fieldText = stringResource(id = R.string.bug_report_title),
            error = titleError,
        )
        JobisBoxTextField(
            hint = stringResource(id = R.string.bug_report_content),
            onValueChanged = onContentChanged,
            value = content,
            fieldText = stringResource(id = R.string.bug_report_content),
            error = contentError,
        )
    }
}

@Composable
private fun ColumnScope.ScreenShots(
    uris: List<Uri>,
    addScreenshot: () -> Unit,
    removeScreenshot: (Int) -> Unit,
) {
    val uriSize = uris.size
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Caption(
            text = "${stringResource(id = R.string.bug_report_screenshots)} ($uriSize/5)",
            color = JobisColor.Gray700,
        )
        Box(modifier = Modifier.height(150.dp)) {
            if (uriSize == 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = JobisColor.Gray400,
                            shape = RoundedCornerShape(4.dp),
                        )
                        .clip(RoundedCornerShape(4.dp))
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
                    Image(
                        painter = painterResource(R.drawable.ic_image),
                        contentDescription = stringResource(id = R.string.content_description_image_bug),
                    )
                }
            }
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                itemsIndexed(uris) { index, uri ->
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
                                imageContentDescription = stringResource(id = R.string.content_description_icon_remove),
                            )
                        }
                    }
                }
                if (uriSize in 1..4) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(end = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            JobisSmallIconButton(
                                drawable = R.drawable.ic_add_blue,
                                onClick = addScreenshot,
                                color = JobisButtonColor.MainShadowColor,
                                shadow = true,
                                imageContentDescription = stringResource(id = R.string.content_description_icon_add),
                            )
                        }
                    }
                }
            }
        }
    }
}
