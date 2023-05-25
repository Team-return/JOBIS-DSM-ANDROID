package team.retum.domain.repository

import team.retum.domain.entity.StudentInformationEntity

interface StudentsRepository {
    suspend fun fetchStudentInformation(): StudentInformationEntity
}