package team.retum.jobis_android.feature.main.mypage

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import team.retum.data.remote.url.JobisUrl
import team.retum.domain.entity.FileType
import team.retum.domain.entity.company.ReviewableCompanyEntity
import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.enums.Department
import team.retum.domain.exception.BadRequestException
import team.retum.domain.param.files.PresignedUrlParam
import team.retum.domain.param.students.EditProfileImageParam
import team.retum.domain.usecase.company.FetchReviewableCompaniesUseCase
import team.retum.domain.usecase.file.CreatePresignedUrlUseCase
import team.retum.domain.usecase.file.UploadPresignedUrlFileUseCase
import team.retum.domain.usecase.student.EditProfileImageUseCase
import team.retum.domain.usecase.student.FetchStudentInformationUseCase
import team.retum.domain.usecase.user.SignOutUseCase
import team.retum.jobis_android.feature.root.BaseViewModel
import team.retum.jobis_android.util.mvi.SideEffect
import team.retum.jobis_android.util.mvi.State
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class MyPageScreenViewModel @Inject constructor(
    private val editProfileImageUseCase: EditProfileImageUseCase,
    private val fetchStudentInformationUseCase: FetchStudentInformationUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val fetchReviewableCompaniesUseCase: FetchReviewableCompaniesUseCase,
    private val createPresignedUrlUseCase: CreatePresignedUrlUseCase,
    private val uploadFileUseCase: UploadPresignedUrlFileUseCase,
) : BaseViewModel<MyPageState, MyPageSideEffect>() {

    override val container = container<MyPageState, MyPageSideEffect>(MyPageState())

    init {
        fetchStudentInformation()
        fetchReviewableCompanies()
    }

    private fun fetchStudentInformation() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchStudentInformationUseCase().onSuccess {
                val profileImageUrl = JobisUrl.imageUrl + it.profileImageUrl
                reduce { state.copy(studentInformation = it.copy(profileImageUrl = profileImageUrl)) }
            }
        }
    }

    private fun fetchReviewableCompanies() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            fetchReviewableCompaniesUseCase().onSuccess {
                reduce { state.copy(reviewableCompanies = it.companies) }
            }
        }
    }

    private fun editProfileImage(profileImageUrl: String) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            editProfileImageUseCase(EditProfileImageParam(profileImageUrl)).onSuccess {
                fetchStudentInformation()
            }.onSuccess {
                fetchStudentInformation()
                postSideEffect(MyPageSideEffect.SuccessEditProfileImage)
            }.onFailure {
                postSideEffect(
                    when (it) {
                        is KotlinNullPointerException -> MyPageSideEffect.SuccessEditProfileImage
                        else -> MyPageSideEffect.Exception(getStringFromException(it))
                    },
                )
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

    internal fun createPresignedUrl(files: List<File>) = intent {
        viewModelScope.launch(Dispatchers.IO) {
            createPresignedUrlUseCase(
                presignedUrlParam = PresignedUrlParam(
                    files = files.map {
                        PresignedUrlParam.File(
                            type = FileType.LOGO_IMAGE,
                            fileName = it.name,
                        )
                    },
                ),
            ).onSuccess {
                uploadFile(
                    presignedUrl = it.urls.first().presignedUrl,
                    file = files.first(),
                )
                editProfileImage(profileImageUrl = it.urls.first().filePath)
            }.onFailure {
                when (it) {
                    is BadRequestException -> {
                        postSideEffect(MyPageSideEffect.InvalidFileExtension)
                    }

                    else -> {
                        val message = getStringFromException(it)
                        postSideEffect(MyPageSideEffect.Exception(message))
                    }
                }
            }
        }
    }

    private fun uploadFile(
        presignedUrl: String,
        file: File,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            uploadFileUseCase(
                presignedUrl = presignedUrl,
                file = file,
            )
        }
    }
}

internal data class MyPageState(
    val studentInformation: StudentInformationEntity = StudentInformationEntity(
        studentName = "",
        studentGcn = "",
        department = Department.DEFAULT,
        profileImageUrl = "",
    ),
    val reviewableCompanies: List<ReviewableCompanyEntity> = emptyList(),
) : State

internal sealed interface MyPageSideEffect : SideEffect {
    object SuccessSignOut : MyPageSideEffect
    object SuccessEditProfileImage : MyPageSideEffect
    class Exception(@StringRes val message: Int) : MyPageSideEffect
    object InvalidFileExtension : MyPageSideEffect
}
