package test.dev.importantpeople.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import test.dev.importantpeople.data.remote.response.GetRandomUserListResponse

interface RandomUserService {
    @GET("?results=20&seed=abc")
    suspend fun getRandomUserList(@Query("page") page: Int): Response<GetRandomUserListResponse>
}