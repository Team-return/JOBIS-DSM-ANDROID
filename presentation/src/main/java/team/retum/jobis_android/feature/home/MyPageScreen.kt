package team.retum.jobis_android.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import kotlinx.coroutines.runBlocking
import team.retum.domain.entity.student.Department
import team.retum.jobis_android.feature.recruitment.Header
import team.retum.jobis_android.util.compose.skeleton
import team.retum.jobis_android.viewmodel.home.HomeViewModel
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Heading6

@Composable
internal fun MyPageScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {

    val state = homeViewModel.container.stateFlow.collectAsState()

    val studentInformation = state.value.studentInformation

    runBlocking {
        homeViewModel.fetchStudentInformations()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 48.dp,
                start = 24.dp,
                end = 24.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(text = stringResource(id = R.string.bottom_nav_my_page))
        Spacer(modifier = Modifier.height(50.dp))
        UserProfile(
            profileImageUrl = studentInformation.profileImageUrl,
            name = studentInformation.studentName,
            department = studentInformation.department,
            studentGcn = studentInformation.studentGcn,
        )

    }
}

@Composable
private fun UserProfile(
    profileImageUrl: String,
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
        number = studentGcn.substring((if (studentGcn[2].toInt() == 0) 3 else 2)..3)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            modifier = Modifier
                .size(62.dp)
                .skeleton(
                    show = profileImageUrl.isEmpty(),
                    shape = CircleShape,
                ),
            model = profileImageUrl,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(6.dp))
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
                .skeleton(
                    show = grade.isEmpty()
                ),
            text = if (grade.isNotEmpty()) stringResource(
                id = R.string.student_gcn,
                grade,
                classRoom,
                number
            )
            else "",
            color = JobisColor.Gray700,
        )
        Spacer(modifier = Modifier.height(80.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = JobisColor.Gray400,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Menu(content = stringResource(id = R.string.bug_report))
        Spacer(modifier = Modifier.height(16.dp))
        Menu(content = stringResource(id = R.string.choose_interests))
        Spacer(modifier = Modifier.height(16.dp))
        Menu(content = stringResource(id = R.string.change_password))
        Spacer(modifier = Modifier.height(16.dp))
        Menu(
            content = stringResource(id = R.string.log_out),
            contentColor = JobisColor.Red,
        )
    }
}

@Composable
private fun Menu(
    content: String,
    contentColor: Color = JobisColor.LightBlue,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
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