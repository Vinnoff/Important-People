package test.dev.importantpeople.data.remote

import retrofit2.Response
import retrofit2.http.GET
import test.dev.importantpeople.data.remote.response.GetRandomUserListResponse

interface RandomUserService {
    @GET("?results=20")
    suspend fun getRandomUserList(): Response<GetRandomUserListResponse>
}