package team.retum.jobis_android.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.jobis.jobis_android.R
import team.retum.domain.entity.student.Department
import team.retum.jobis_android.feature.recruitment.Header
import team.retum.jobisui.colors.JobisColor
import team.returm.jobisdesignsystem.theme.Body2
import team.returm.jobisdesignsystem.theme.Body4
import team.returm.jobisdesignsystem.theme.Heading6

@Composable
internal fun MyPageScreen(
    navController: NavController,
) {
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
            profileImageUrl = "",
            name = "승훈 정",
            department = Department.AI_SOFTWARE,
            studentGcn = "2205",
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
    val grade = studentGcn[0]
    val classRoom = studentGcn[1]
    val number = studentGcn.substring((if (studentGcn[2].toInt() == 0) 3 else 2)..3)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            modifier = Modifier.size(62.dp),
            model = profileImageUrl,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Heading6(text = name)
        Body2(text = department.department)
        Body4(
            text = stringResource(id = R.string.student_gcn, grade, classRoom, number),
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