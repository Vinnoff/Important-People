package test.dev.importantpeople.data.repository

import test.dev.importantpeople.data.remote.response.GetRandomUserListResponse
import test.dev.importantpeople.data.remote.source.RemoteDataSource

class UserRepositoryImpl(private val remoteDataSource: RemoteDataSource) : UserRepository {
    override suspend fun getRandomUserList(): GetRandomUserListResponse? {
        return remoteDataSource.getRandomUserList()
    }
}
