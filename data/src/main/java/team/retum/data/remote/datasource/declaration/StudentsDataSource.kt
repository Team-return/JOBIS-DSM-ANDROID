package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.students.FetchStudentInformationResponse

interface StudentsDataSource {
    suspend fun fetchStudentInformation(): FetchStudentInformationResponse
}