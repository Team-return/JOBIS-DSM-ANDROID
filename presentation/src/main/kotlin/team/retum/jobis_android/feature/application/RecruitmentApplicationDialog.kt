package team.retum.jobis_android.feature.application

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jobis.jobis_android.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import team.retum.jobis_android.LocalAppState
import team.retum.jobis_android.contract.application.ApplicationSideEffect
import team.retum.jobis_android.contract.file.FileSideEffect
import team.retum.jobis_android.util.FileUtil
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobis_android.viewmodel.application.ApplicationViewModel
import team.retum.jobis_android.viewmodel.file.FileViewModel
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisMediumButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable

@Composable
internal fun RecruitmentApplicationDialog(
    isReApply: Boolean = false,
    recruitmentId: Long,
    fileViewModel: FileViewModel = hiltViewModel(),
    applicationViewModel: ApplicationViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
) {
    val appState = LocalAppState.current

    val context = LocalContext.current

    val fileState by fileViewModel.container.stateFlow.collectAsStateWithLifecycle()
    val applicationState by applicationViewModel.container.stateFlow.collectAsStateWithLifecycle()

    val files = fileState.files
    val urls = remember { mutableStateListOf<String>() }

    var fileCount by remember { mutableStateOf(0) }
    var urlCount by remember { mutableStateOf(0) }

    val fileLargeMessage = stringResource(id = R.string.recruitment_application_file_too_large)

    LaunchedEffect(Unit) {
        applicationViewModel.setRecruitmentId(recruitmentId = recruitmentId)
        fileViewModel.container.sideEffectFlow.collect {
            when (it) {
                is FileSideEffect.SuccessUploadFile -> {
                    applicationViewModel.setAttachments(fileUrls = it.fileUrls)
                }

                is FileSideEffect.FileLargeException -> {
                    appState.showErrorToast(fileLargeMessage)
                }

                is FileSideEffect.Exception -> {
                    appState.showErrorToast(message = it.message)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        applicationViewModel.container.sideEffectFlow.collect {
            onDismissRequest()
            when (it) {
                is ApplicationSideEffect.SuccessApplyCompany -> {
                    appState.showSuccessToast(context.getString(R.string.recruitment_application_success))
                }

                is ApplicationSideEffect.RecruitmentNotFound -> {
                    appState.showErrorToast(context.getString(R.string.recruitment_application_not_found))
                }

                is ApplicationSideEffect.ApplyConflict -> {
                    appState.showErrorToast(context.getString(R.string.recruitment_application_conflict))
                }

                is ApplicationSideEffect.Exception -> {
                    appState.showErrorToast(it.message)
                }
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val onClickConfirmButton: () -> Unit = {
        applicationViewModel.setUrls(urls = urls)
        if (isReApply) {
            applicationViewModel.reApplyCompany()
        } else {
            applicationViewModel.applyCompany()
        }
        applicationViewModel.setButtonState(buttonState = false)
        coroutineScope.launch {
            delay(3000)
            applicationViewModel.setButtonState(buttonState = true)
        }
    }

    val addFile = { uri: Uri? ->
        uri?.run {
            fileViewModel.addFile(
                FileUtil.toFile(
                    context = context,
                    uri = uri,
                ),
            )
            applicationViewModel.setButtonState(fileCount > 0)
        }
    }

    val onAddFile: (ActivityResult) -> Unit = { result: ActivityResult ->
        val clipData = result.data?.clipData
        if (clipData != null) {
            repeat(clipData.itemCount) {
                val item = clipData.getItemAt(it)
                if (item != null) {
                    addFile(item.uri)
                    fileCount += 1
                }

            }
        } else {
            result.data?.data?.run {
                addFile(this)
                fileCount += 1
            }
        }
        applicationViewModel.setButtonState(fileCount > 0)
        fileViewModel.uploadFile()

    }

    val onRemoveFile = { index: Int ->
        fileViewModel.removeFile(index)
        fileCount -= 1
        applicationViewModel.setButtonState(fileCount > 0)
    }

    val onAddUrl: () -> Unit = {
        urls.add("")
        urlCount += 1
        applicationViewModel.setButtonState(fileCount > 0)
    }

    val onRemoveUrl = { index: Int ->
        urls.removeAt(index)
        urlCount -= 1
    }

    val onUrlChanged = { index: Int, url: String ->
        urls[index] = url
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = onAddFile,
    )

    Column(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .clip(shape = RoundedCornerShape(14.dp))
            .background(JobisColor.Gray100)
            .padding(
                horizontal = 18.dp,
                vertical = 18.dp,
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Header(
            text = stringResource(
                id = if (isReApply) R.string.do_re_apply
                else R.string.do_apply,
            ),
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .padding(
                    top = 24.dp,
                )
                .verticalScroll(rememberScrollState()),
        ) {
            Caption(
                modifier = Modifier.padding(
                    bottom = 6.dp,
                ),
                text = stringResource(id = R.string.attached_file),
                color = JobisColor.Gray600,
            )
            Column(
                modifier = Modifier.padding(bottom = 6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                repeat(fileCount) {
                    AttachedFile(
                        fileName = files[it].name,
                        fileSize = (files[it].length() / 1024).toString(),
                        onClick = { onRemoveFile(it) },
                    )
                }
            }
            SubmitSpace(description = stringResource(id = R.string.add_to_press_file)) {
                Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "*/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    addCategory(Intent.CATEGORY_OPENABLE)
                    launcher.launch(this)
                }
            }
            Caption(
                modifier = Modifier.padding(top = 18.dp),
                text = stringResource(id = R.string.url),
                color = JobisColor.Gray600,
            )
            Column(
                modifier = Modifier.padding(vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                repeat(urlCount) { index ->
                    AttachedUrl(
                        onValueChanged = { onUrlChanged(index, it) },
                        url = urls[index],
                        onRemoveUrl = { onRemoveUrl(index) },
                    )
                }
            }
            SubmitSpace(
                modifier = Modifier.padding(bottom = 8.dp),
                description = stringResource(id = R.string.add_to_press_url),
                onClick = onAddUrl,
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 96.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            JobisMediumButton(
                text = stringResource(id = R.string.check),
                color = JobisButtonColor.MainSolidColor,
                enabled = applicationState.buttonState,
                onClick = onClickConfirmButton,
            )
        }
    }
}

@Composable
private fun SubmitSpace(
    modifier: Modifier = Modifier,
    description: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
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
            .jobisClickable(
                rippleEnabled = true,
                onClick = onClick,
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = JobisIcon.Upload),
            contentDescription = null,
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
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Row {
            Caption(
                text = "$fileName $fileSize KB".take(38),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        RemoveIcon(onClick = onClick)
        Spacer(modifier = Modifier.width(14.dp))
    }
}

@Composable
private fun AttachedUrl(
    onValueChanged: (String) -> Unit,
    url: String,
    onRemoveUrl: () -> Unit,
) {
    JobisBoxTextField(
        onValueChanged = onValueChanged,
        value = url,
        hint = stringResource(id = R.string.input_url),
        icon = { RemoveIcon(onClick = onRemoveUrl) },
    )
}

@Composable
private fun RemoveIcon(onClick: () -> Unit) {
    Image(
        modifier = Modifier
            .padding(top = 2.dp)
            .jobisClickable(onClick = onClick),
        painter = painterResource(id = JobisIcon.Close),
        contentDescription = null,
    )
}
