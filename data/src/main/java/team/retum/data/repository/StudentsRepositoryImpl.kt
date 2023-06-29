package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.StudentsDataSource
import team.retum.data.remote.request.students.toRequest
import team.retum.data.remote.response.students.toEntity
import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.param.students.ResetPasswordParam
import team.retum.domain.repository.StudentsRepository
import javax.inject.Inject

class StudentsRepositoryImpl @Inject constructor(
    private val studentsDataSource: StudentsDataSource,
): StudentsRepository {
    override suspend fun fetchStudentInformation(): StudentInformationEntity =
        studentsDataSource.fetchStudentInformation().toEntity()

    override suspend fun comparePassword(
        password: String,
    ) {
        studentsDataSource.comparePassword(password = password)
    }

    override suspend fun resetPassword(
        resetPasswordParam: ResetPasswordParam,
    ) {
        studentsDataSource.resetPassword(
            resetPasswordRequest = resetPasswordParam.toRequest(),
        )
    }
}