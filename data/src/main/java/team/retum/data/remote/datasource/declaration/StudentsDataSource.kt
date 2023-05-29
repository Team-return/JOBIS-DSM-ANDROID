package team.retum.data.remote.datasource.declaration

import team.retum.data.remote.response.FetchStudentInformationResponse

interface StudentsDataSource {
    suspend fun fetchStudentInformation(): FetchStudentInformationResponse
}