package test.dev.importantpeople.domain.entity.user

import org.threeten.bp.LocalDateTime
import java.util.*


data class UserEntity(
    val uuid: String,
    val title: Gender?,
    val firstname: String,
    val lastname: String,
    val username: String,
    val pictures: PicturesEntity?,
    val contacts: ContactsEntity?,
    val birthday: LocalDateTime?,
    val registeredDate: LocalDateTime?,
    val nationality: Locale?,
    val location: LocationEntity?,
)

data class PicturesEntity(
    val thumbnail: String?,
    val normal: String?,
)

data class ContactsEntity(
    val email: String?,
    val phone: String?,
    val cell: String?,
)

data class LocationEntity(
    val street: String,
    val city: String,
    val state: String?,
    val country: String,
    val postalCode: String?,
    val coordinates: CoordinatesEntity?,
)

data class CoordinatesEntity(
    val latitude: String,
    val longitude: String,
)

enum class Gender {
    MAN,
    WOMAN;

    companion object {
        fun fromString(string: String?) = when (string) {
            "male" -> MAN
            "female" -> WOMAN
            else -> null
        }
    }
}
