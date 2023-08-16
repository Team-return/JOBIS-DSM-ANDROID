package team.retum.jobis_android.viewmodel.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.domain.param.students.EditProfileImageParam
import team.retum.domain.usecase.student.EditProfileImageUseCase
import team.retum.jobis_android.contract.mypage.MyPageSideEffect
import team.retum.jobis_android.contract.mypage.MyPageState
import team.retum.jobis_android.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    private val editProfileImageUseCase: EditProfileImageUseCase
) : BaseViewModel<MyPageState, MyPageSideEffect>() {

    override val container = container<MyPageState, MyPageSideEffect>(MyPageState())

    private fun fetchStudentInformation() {}

    internal fun editProfileImage() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            editProfileImageUseCase(EditProfileImageParam(state.editProfileImageUrl)).onSuccess {
                fetchStudentInformation()
            }.onSuccess {
                fetchStudentInformation()
            }.onFailure {
                postSideEffect(MyPageSideEffect.Exception(getStringFromException(it)))
            }
        }
    }

    private fun setProfileImageUrl(profileImageUrl: String) = intent {
        reduce {
            state.copy(profileImageUrl = profileImageUrl)
        }
    }

    internal fun setEditProfileImageUrl(profileImageUrl: String) = intent {
        reduce {
            state.copy(editProfileImageUrl = profileImageUrl)
        }
    }
}