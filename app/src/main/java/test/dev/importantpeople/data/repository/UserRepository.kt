package test.dev.importantpeople.data.repository

import test.dev.importantpeople.data.remote.response.GetRandomUserListResponse

interface UserRepository {
    suspend fun getRandomUserList(): GetRandomUserListResponse?
}
