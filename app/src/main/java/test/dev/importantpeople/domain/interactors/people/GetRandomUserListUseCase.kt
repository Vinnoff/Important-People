package test.dev.importantpeople.domain.interactors.people

import test.dev.importantpeople.data.remote.response.ResultResponse
import test.dev.importantpeople.data.repository.UserRepository

class GetRandomUserListUseCase(private val userRepository: UserRepository) {
    suspend fun invoke(): List<ResultResponse>? {
        return userRepository.getRandomUserList()?.results
    }
}