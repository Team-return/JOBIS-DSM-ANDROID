package team.retum.domain.repository

import team.retum.domain.entity.student.StudentInformationEntity

interface StudentsRepository {
    suspend fun fetchStudentInformation(): StudentInformationEntity
}