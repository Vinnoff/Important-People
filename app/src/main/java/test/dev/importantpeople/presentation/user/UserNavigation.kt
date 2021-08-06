package test.dev.importantpeople.presentation.user

sealed class UserNavigation {
    object LIST : UserNavigation()
    object DETAILS : UserNavigation()
}
