package team.retum.data.repository

import team.retum.data.remote.datasource.declaration.StudentsDataSource
import team.retum.data.remote.response.applications.toEntity
import team.retum.domain.entity.student.StudentInformationEntity
import team.retum.domain.repository.StudentsRepository
import javax.inject.Inject

class StudentsRepositoryImpl @Inject constructor(
    private val studentsDataSource: StudentsDataSource,
): StudentsRepository {
    override suspend fun fetchStudentInformation(): StudentInformationEntity =
        studentsDataSource.fetchStudentInformation().toEntity()
}