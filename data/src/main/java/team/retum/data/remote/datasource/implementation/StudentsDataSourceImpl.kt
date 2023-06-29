package team.retum.data.remote.datasource.implementation

import team.retum.data.remote.api.StudentsApi
import team.retum.data.remote.datasource.declaration.StudentsDataSource
import team.retum.data.remote.response.students.FetchStudentInformationResponse
import team.retum.data.util.HttpHandler
import javax.inject.Inject

class StudentsDataSourceImpl @Inject constructor(
    private val studentsApi: StudentsApi,
): StudentsDataSource {
    override suspend fun fetchStudentInformation(): FetchStudentInformationResponse =
        HttpHandler<FetchStudentInformationResponse>().httpRequest {
            studentsApi.fetchStudentInformation()
        }.sendRequest()

    override suspend fun comparePassword(password: String) = HttpHandler<Unit>().httpRequest {
        studentsApi.comparePassword(password = password)
    }.sendRequest()
}