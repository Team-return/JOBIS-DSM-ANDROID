package team.retum.jobis_android.feature.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import team.retum.jobis_android.feature.home.BookmarkRecruitmentsScreen
import team.retum.jobis_android.feature.home.HomeScreen
import team.retum.jobis_android.feature.home.MenuScreen
import team.retum.jobis_android.feature.home.MyPageScreen
import team.retum.jobis_android.feature.recruitment.RecruitmentFilter
import team.retum.jobis_android.root.navigation.NavigationRoute
import team.retum.jobis_android.root.navigation.navigateToBookmarkRecruitments
import team.retum.jobis_android.root.navigation.navigateToMyPage
import team.retum.jobis_android.util.compose.navigation.BottomBar

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navigateToRecruitments: () -> Unit,
    navigateToCompanies: () -> Unit,
    navigateToRecruitmentDetails: (Long) -> Unit,
    navigateToSignInPopUpWithMain: () -> Unit,
    navigateToBugReport: () -> Unit,
    navigateToComparePassword: () -> Unit,
    navigateToPostReview: (Long) -> Unit,
    navigateToNotifications: () -> Unit,
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
                sheetState = sheetState.isVisible,
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
                startDestination = NavigationRoute.Navigation.Home,
            ) {
                composable(route = NavigationRoute.Navigation.Home) {
                    HomeScreen(
                        navigateToMyPage = navHostController::navigateToMyPage,
                        navigateToRecruitments = navigateToRecruitments,
                        navigateToCompanies = navigateToCompanies,
                        navigateToNotifications = navigateToNotifications,
                    )
                }
                composable(route = NavigationRoute.Navigation.BookmarkRecruitments) {
                    BookmarkRecruitmentsScreen(
                        navigateToRecruitmentDetails = navigateToRecruitmentDetails,
                        navigateToRecruitments = navigateToRecruitments,
                    )
                }
                composable(route = NavigationRoute.Navigation.MyPage) {
                    MyPageScreen(
                        navigateToSignInPopUpWithMain = navigateToSignInPopUpWithMain,
                        navigateToBugReport = navigateToBugReport,
                        navigateToComparePassword = navigateToComparePassword,
                        navigateToPostReview = navigateToPostReview,
                        navigateToNotifications = navigateToNotifications,
                    )
                }
                composable(route = NavigationRoute.Navigation.Menu) {
                    MenuScreen(
                        navigateToMyPage = navHostController::navigateToMyPage,
                        navigateToRecruitments = navigateToRecruitments,
                        navigateToCompanies = navigateToCompanies,
                        navigateToBookmarkRecruitments = navHostController::navigateToBookmarkRecruitments,
                    )
                }
            }
        }
    }
}