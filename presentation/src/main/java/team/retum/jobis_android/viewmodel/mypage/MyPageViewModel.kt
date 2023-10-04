package team.retum.jobis_android.viewmodel.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.data.remote.url.JobisUrl
import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.param.students.EditProfileImageParam
import team.retum.domain.usecase.student.EditProfileImageUseCase
import team.retum.domain.usecase.student.FetchStudentInformationUseCase
import team.retum.domain.usecase.user.SignOutUseCase
import team.retum.jobis_android.contract.mypage.MyPageSideEffect
import team.retum.jobis_android.contract.mypage.MyPageState
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    private val editProfileImageUseCase: EditProfileImageUseCase,
    private val fetchStudentInformationUseCase: FetchStudentInformationUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<MyPageState, MyPageSideEffect>() {

    override val container = container<MyPageState, MyPageSideEffect>(MyPageState())

    init {
        fetchStudentInformation()
    }

    private fun fetchStudentInformation() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchStudentInformationUseCase().onSuccess {
                setStudentInformation(studentInformationEntity = it)
            }
        }
    }

    internal fun editProfileImage() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            editProfileImageUseCase(EditProfileImageParam(state.editProfileImageUrl)).onSuccess {
                fetchStudentInformation()
            }.onSuccess {
                fetchStudentInformation()
                postSideEffect(MyPageSideEffect.SuccessEditProfileImage)
            }.onFailure {
                postSideEffect(MyPageSideEffect.Exception(getStringFromException(it)))
            }
        }
    }

    internal fun signOut() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            signOutUseCase().onSuccess {
                postSideEffect(MyPageSideEffect.SuccessSignOut)
            }
        }
    }

    private fun setStudentInformation(studentInformationEntity: StudentInformationEntity) = intent {
        reduce {
            with(studentInformationEntity) {
                state.copy(
                    studentName = studentName,
                    studentGcn = studentGcn,
                    department = department,
                    profileImageUrl = JobisUrl.imageUrl + profileImageUrl,
                )
            }
        }
    }

    internal fun setEditProfileImageUrl(profileImageUrl: String) = intent {
        reduce {
            state.copy(editProfileImageUrl = profileImageUrl)
        }
    }
}