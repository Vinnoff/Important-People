package test.dev.importantpeople.presentation.user

sealed class UserNavigation {
    object LIST : UserNavigation()
    object DETAILS : UserNavigation()
    data class PHONE(val phone: String) : UserNavigation()
    data class EMAIL(val email: String) : UserNavigation()
    data class NAVIGATION(val username: String, val latitude: String, val longitude: String) : UserNavigation()
}
