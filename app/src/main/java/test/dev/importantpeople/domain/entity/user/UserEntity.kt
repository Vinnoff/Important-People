package test.dev.importantpeople.domain.entity.user

sealed class UserEntity {
    object EMPTY_DATA : UserEntity()
    object ERROR : UserEntity()
    data class SUCCESS(val data: List<UserData>) : UserEntity()
}