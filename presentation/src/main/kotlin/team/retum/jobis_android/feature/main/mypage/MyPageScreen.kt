package team.retum.jobis_android.feature.main.mypage

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import team.retum.domain.enums.Department
import team.retum.jobis_android.LocalAppState
import team.retum.jobis_android.util.FileUtil
import team.retum.jobis_android.util.compose.animation.skeleton
import team.retum.jobis_android.util.compose.component.Header
import team.returm.jobisdesignsystem.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Caption
import team.returm.jobisdesignsystem.theme.Heading6
import team.returm.jobisdesignsystem.util.jobisClickable
import java.io.File

@Composable
internal fun MyPageScreen(
    navigateToSignInPopUpWithMain: () -> Unit,
    navigateToBugReport: () -> Unit,
    navigateToComparePassword: () -> Unit,
    navigateToPostReview: (Long) -> Unit,
    navigateToNotifications: () -> Unit,
    myPageScreenViewModel: MyPageScreenViewModel = hiltViewModel(),
) {
    val state by myPageScreenViewModel.collectAsState()
    val studentInformation = state.studentInformation
    val reviewableCompanies = state.reviewableCompanies
    val context = LocalContext.current
    val files = remember { mutableStateListOf<File>() }
    var showSignOutDialog by remember { mutableStateOf(false) }
    val activityResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                files.add(
                    FileUtil.toFile(
                        context = context,
                        uri = uri,
                    ),
                )
                myPageScreenViewModel.createPresignedUrl(files = files)
            }
        }

    val editProfileImage: () -> Unit = {
        val request = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        activityResultLauncher.launch(request)
    }

    LocalAppState.current.run {
        myPageScreenViewModel.collectSideEffect {
            when (it) {
                is MyPageSideEffect.SuccessSignOut -> {
                    navigateToSignInPopUpWithMain()
                }

                is MyPageSideEffect.SuccessEditProfileImage -> {
                    showSuccessToast(context.getString(R.string.success_edit_profile_image))
                    files.clear()
                }

                is MyPageSideEffect.InvalidFileExtension -> {
                    showErrorToast(context.getString(R.string.recruitment_application_invalid_file_extension))
                }

                is MyPageSideEffect.Exception -> {
                    showErrorToast(context.getString(it.message))
                }
            }
        }
    }

    if (showSignOutDialog) {
        Dialog(onDismissRequest = { showSignOutDialog = false }) {
            SignOutDialog(
                title = stringResource(id = R.string.sign_out_dialog_title),
                mainBtnText = stringResource(id = R.string.check),
                subBtnText = stringResource(id = R.string.cancel),
                onMainBtnClick = myPageScreenViewModel::signOut,
                onSubBtnClick = { showSignOutDialog = false },
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Header(
            text = stringResource(id = R.string.bottom_nav_my_page),
            onClick = navigateToNotifications,
        )
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(50.dp))
            UserProfile(
                profileImageUrl = studentInformation.profileImageUrl,
                editProfileImage = editProfileImage,
                name = studentInformation.studentName,
                department = studentInformation.department,
                studentGcn = studentInformation.studentGcn,
            )
            Spacer(modifier = Modifier.height(32.dp))
            if (reviewableCompanies.isNotEmpty()) {
                val reviewableCompany = reviewableCompanies[0]
                AvailablePostReviewCompany(
                    companyName = reviewableCompany.name,
                    companyId = reviewableCompany.id,
                    navigateToPostReview = navigateToPostReview,
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            UserMenu(
                navigateBugReport = navigateToBugReport,
                navigateToComparePassword = navigateToComparePassword,
                onSignOutClicked = { showSignOutDialog = true },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun UserProfile(
    profileImageUrl: String,
    editProfileImage: () -> Unit,
    name: String,
    department: Department,
    studentGcn: String,
) {
    var grade = ""
    var classRoom = ""
    var number = ""

    if (studentGcn.isNotEmpty()) {
        grade = studentGcn[0].toString().ifEmpty { "" }
        classRoom = studentGcn[1].toString().ifEmpty { "" }
        number =
            studentGcn.substring((if (Integer.parseInt(studentGcn[2].toString()) == 0) 3 else 2)..3)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .jobisClickable(
                        rippleEnabled = true,
                        onClick = editProfileImage,
                    )
                    .skeleton(
                        show = profileImageUrl.isEmpty(),
                        shape = CircleShape,
                    ),
                model = profileImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            if (profileImageUrl.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(JobisColor.LightBlue),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = stringResource(id = R.string.content_description_icon_edit),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(22.dp))
        Column {
            Heading6(
                modifier = Modifier
                    .defaultMinSize(minWidth = 56.dp)
                    .skeleton(show = name.isEmpty()),
                text = name,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Body2(
                modifier = Modifier
                    .defaultMinSize(minWidth = 164.dp)
                    .skeleton(show = department == Department.DEFAULT),
                text = department.department,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Body4(
                modifier = Modifier
                    .defaultMinSize(minWidth = 82.dp)
                    .skeleton(show = grade.isEmpty()),
                text = if (grade.isNotEmpty()) {
                    stringResource(
                        id = R.string.student_gcn,
                        grade,
                        classRoom,
                        number,
                    )
                } else {
                    ""
                },
                color = JobisColor.Gray700,
            )
        }
    }
}

@Stable
val AvailablePostReviewCompanyShape = RoundedCornerShape(14.dp)

@Composable
private fun AvailablePostReviewCompany(
    companyName: String,
    companyId: Long,
    navigateToPostReview: (Long) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp)
            .shadow(
                elevation = 8.dp,
                shape = AvailablePostReviewCompanyShape,
            )
            .clip(AvailablePostReviewCompanyShape)
            .background(
                color = JobisColor.Gray100,
                shape = AvailablePostReviewCompanyShape,
            )
            .jobisClickable(
                rippleEnabled = true,
                onClick = { navigateToPostReview(companyId) },
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.width(64.dp))
        Caption(
            modifier = Modifier.padding(vertical = 14.dp),
            text = companyName,
            color = JobisColor.Gray600,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Caption(
            text = stringResource(id = R.string.post_review_do_post_review),
            color = JobisColor.LightBlue,
        )
        Spacer(modifier = Modifier.width(64.dp))
    }
}

@Composable
private fun UserMenu(
    navigateBugReport: () -> Unit,
    navigateToComparePassword: () -> Unit,
    onSignOutClicked: () -> Unit,
) {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = JobisColor.Gray400,
    )
    Menu(
        content = stringResource(id = R.string.bug_report),
        onClick = navigateBugReport,
    )
    Menu(
        content = stringResource(id = R.string.change_password),
        onClick = navigateToComparePassword,
    )
    Menu(
        content = stringResource(id = R.string.log_out),
        contentColor = JobisColor.Red,
        onClick = onSignOutClicked,
    )
}

@Composable
private fun Menu(
    content: String,
    contentColor: Color = JobisColor.LightBlue,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .jobisClickable(rippleEnabled = true) {
                onClick()
            }
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Body4(
            text = content,
            color = contentColor,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = JobisColor.Gray400,
        )
    }
}

// TODO move to design system
@Composable
private fun SignOutDialog(
    title: String,
    mainBtnText: String,
    subBtnText: String,
    onMainBtnClick: () -> Unit,
    onSubBtnClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(JobisColor.Gray100),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Body2(
            text = title,
            color = JobisColor.Red,
        )
        Spacer(modifier = Modifier.height(26.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(horizontal = 12.dp),
            color = JobisColor.Gray400,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .height(40.dp)
                    .jobisClickable(
                        onClick = onSubBtnClick,
                        rippleEnabled = true,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Body4(
                    text = subBtnText,
                    color = JobisColor.Gray600,
                )
            }
            Divider(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
                    .padding(vertical = 4.dp),
            )
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .height(40.dp)
                    .jobisClickable(
                        onClick = onMainBtnClick,
                        rippleEnabled = true,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Body4(
                    text = mainBtnText,
                    color = JobisColor.Gray800,
                )
            }
        }
    }
}
