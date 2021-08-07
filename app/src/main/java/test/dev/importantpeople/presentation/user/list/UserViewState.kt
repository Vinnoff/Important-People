package test.dev.importantpeople.presentation.user.list

import test.dev.importantpeople.domain.entity.user.UserData

sealed class UserViewState {
    object LOADER : UserViewState()
    object EMPTY_DATA : UserViewState()
    object ERROR : UserViewState()
    data class SUCCESS(val pagination: Int, val data: List<UserData>) : UserViewState()
}
