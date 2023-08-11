package team.retum.jobis_android.feature.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import team.retum.jobis_android.feature.home.BookmarkRecruitmentsScreen
import team.retum.jobis_android.feature.home.HomeScreen
import team.retum.jobis_android.feature.home.MenuScreen
import team.retum.jobis_android.feature.home.MyPageScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentFilter
import team.retum.jobis_android.root.navigation.JobisRoute
import team.retum.jobis_android.util.compose.navigation.BottomBar

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navigateToMyPage: () -> Unit,
    navigateToRecruitments: () -> Unit,
    navigateToCompanies: () -> Unit,
) {

    val scaffoldState = rememberScaffoldState()

    val navHostController = rememberNavController()

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = {
            RecruitmentFilter(
                onDismissDialog = { _, _ ->
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                },
            )
        },
        sheetState = sheetState,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomBar(navController = navHostController)
            },
        ) {
            NavHost(
                modifier = Modifier.padding(it),
                navController = navHostController,
                startDestination = JobisRoute.Navigation.Home,
            ) {
                composable(route = JobisRoute.Navigation.Home) {
                    HomeScreen(
                        navigateToMyPage = navigateToMyPage,
                        navigateToRecruitments = navigateToRecruitments,
                        navigateToCompanies = navigateToCompanies,
                    )
                }

                composable(route = JobisRoute.Navigation.BookmarkRecruitments) {
                    BookmarkRecruitmentsScreen(navController = navController)
                }

                composable(route = JobisRoute.Navigation.MyPage) {
                    MyPageScreen(navController = navController) {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    }
                }

                composable(route = JobisRoute.Navigation.Menu) {
                    MenuScreen(
                        navController = navController,
                        navHostController = navHostController,
                    )
                }
            }
        }
    }
}