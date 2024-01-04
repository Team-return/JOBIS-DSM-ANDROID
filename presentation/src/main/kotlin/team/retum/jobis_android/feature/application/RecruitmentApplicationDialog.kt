package team.retum.jobis_android.feature.application

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.compose.collectSideEffect
import team.retum.jobis_android.LocalAppState
import team.retum.jobis_android.feature.common.FileSideEffect
import team.retum.jobis_android.feature.common.FileViewModel
import team.retum.jobis_android.util.FileUtil
import team.retum.jobis_android.util.compose.component.Header
import team.retum.jobisui.colors.JobisButtonColor
import team.returm.jobisdesignsystem.button.JobisMediumButton
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.icon.JobisIcon
import team.returm.jobisdesignsystem.textfield.JobisBoxTextField
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.util.jobisClickable
import java.io.File

@Composable
internal fun RecruitmentApplicationDialog(
    isReApply: Boolean = false,
    recruitmentId: Long,
    fileViewModel: FileViewModel = hiltViewModel(),
    applicationViewModel: ApplicationViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    val filePaths = fileViewModel.filePaths
    val files = fileViewModel.files

    LaunchedEffect(Unit) {
        applicationViewModel.setRecruitmentId(recruitmentId = recruitmentId)
    }

    fileViewModel.collectSideEffect {
        when (it) {
            is FileSideEffect.Success -> {
                applicationViewModel.run {
                    if (isReApply) {
                        reApplyCompany(filePaths)
                    } else {
                        applyCompany(filePaths)
                    }
                }
            }

            is FileSideEffect.InvalidFileExtension -> {
                context.getString(R.string.recruitment_application_invalid_file_extension)
            }
        }
    }

    LocalAppState.current.run {
        applicationViewModel.collectSideEffect {
            fileViewModel.resetFiles()
            onDismissRequest()
            when (it) {
                is ApplicationSideEffect.SuccessApplyCompany -> {
                    onDismissRequest()
                    showSuccessToast(context.getString(R.string.recruitment_application_success))
                }

                is ApplicationSideEffect.RecruitmentNotFound -> {
                    showErrorToast(context.getString(R.string.recruitment_application_not_found))
                }

                is ApplicationSideEffect.ApplyConflict -> {
                    showErrorToast(context.getString(R.string.recruitment_application_conflict))
                }

                is ApplicationSideEffect.Exception -> {
                    showErrorToast(context.getString(it.message))
                }
            }
        }
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            with(result.data) {
                when (this?.clipData) {
                    // 파일 한 개인 경우
                    null -> {
                        this?.data?.run {
                            fileViewModel.addFile(
                                FileUtil.toFile(
                                    context = context,
                                    uri = this,
                                ),
                            )
                        }
                    }
                    // 파일 두 개 이상인 경우
                    else -> {
                        repeat(this.clipData?.itemCount ?: 0) {
                            fileViewModel.addFile(
                                FileUtil.toFile(
                                    context = context,
                                    uri = this.clipData?.getItemAt(it)?.uri!!,
                                ),
                            )
                        }
                    }
                }
            }
        }

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
                id = if (isReApply) {
                    R.string.do_re_apply
                } else {
                    R.string.do_apply
                },
            ),
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .padding(top = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Caption(
                modifier = Modifier.padding(bottom = 6.dp),
                text = stringResource(id = R.string.attached_file),
                color = JobisColor.Gray600,
            )
            Column(
                modifier = Modifier.padding(bottom = 6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                files.forEachIndexed { index, file ->
                    AttachedFile(
                        fileName = file.name,
                        fileSize = file.getFileSize(),
                        onClick = { fileViewModel.removeFile(index) },
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
                applicationViewModel.urls.forEachIndexed { index, url ->
                    AttachedUrl(
                        onValueChanged = { applicationViewModel.urls[index] = it },
                        url = url,
                        onRemoveUrl = { applicationViewModel.urls.removeAt(index) },
                    )
                }
            }
            SubmitSpace(
                modifier = Modifier.padding(bottom = 8.dp),
                description = stringResource(id = R.string.add_to_press_url),
                onClick = { applicationViewModel.urls.add("") },
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
                enabled = files.isNotEmpty(),
                onClick = fileViewModel::createPresignedUrl,
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
            .padding(
                start = 10.dp,
                top = 8.dp,
                bottom = 8.dp,
                end = 14.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Caption(
            text = "$fileName $fileSize KB".take(38),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.weight(1f))
        RemoveIcon(onClick = onClick)
    }
}

@Composable
private fun AttachedUrl(
    onValueChanged: (String) -> Unit,
    url: String,
    onRemoveUrl: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd,
    ) {
        JobisBoxTextField(
            onValueChanged = onValueChanged,
            value = url,
            hint = stringResource(id = R.string.input_url),
            icon = { RemoveIcon(onClick = onRemoveUrl) },
        )
        RemoveIcon(
            modifier = Modifier.padding(end = 14.dp),
            onClick = onRemoveUrl,
        )
    }
}

@Composable
private fun RemoveIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .jobisClickable(
                onClick = onClick,
                rippleEnabled = true,
            )
            .padding(4.dp),
        tint = JobisColor.Gray500,
        painter = painterResource(id = JobisIcon.Close),
        contentDescription = null,
    )
}

private fun File.getFileSize() = "${this.length() / 1024}"
