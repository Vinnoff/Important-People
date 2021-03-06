package test.dev.importantpeople.data.remote.source

import test.dev.importantpeople.data.remote.RandomUserService
import test.dev.importantpeople.data.remote.response.GetRandomUserListResponse

class RemoteDataSource(private val service: RandomUserService) {
    suspend fun getRandomUserList(page: Int): GetRandomUserListResponse? {
        val response = service.getRandomUserList(page)
        return when {
            response.isSuccessful -> response.body()
            else -> null
        }
    }
}