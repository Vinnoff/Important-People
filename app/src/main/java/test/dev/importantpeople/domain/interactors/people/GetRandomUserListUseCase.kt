package test.dev.importantpeople.domain.interactors.people

import org.threeten.bp.format.DateTimeFormatter
import test.dev.importantpeople.common.utils.safeLet
import test.dev.importantpeople.common.utils.toLocalDateTime
import test.dev.importantpeople.common.utils.tryOrNull
import test.dev.importantpeople.data.remote.response.LocationResponse
import test.dev.importantpeople.data.remote.response.PictureResponse
import test.dev.importantpeople.data.remote.response.ResultResponse
import test.dev.importantpeople.data.repository.UserRepository
import test.dev.importantpeople.domain.entity.user.*
import java.util.*

class GetRandomUserListUseCase(private val userRepository: UserRepository) {
    suspend fun invoke(): List<UserEntity>? {
        return userRepository.getRandomUserList()?.results?.mapNotNull { it.toEntity() }
    }
}

private fun ResultResponse?.toEntity() = this?.run {
    safeLet(login?.uuid, name?.title, name?.first, name?.last, login?.username) { (uuid, title, firstname, lastname, username) ->
        UserEntity(
            uuid = uuid,
            title = title,
            gender = Gender.fromString(gender),
            firstname = firstname,
            lastname = lastname,
            username = username,
            pictures = picture.toEntity(),
            contacts = getContacts(email, phone, cell),
            birthday = tryOrNull { dob?.date?.toLocalDateTime(DateTimeFormatter.ISO_ZONED_DATE_TIME) },
            registeredDate = tryOrNull { registered?.date?.toLocalDateTime(DateTimeFormatter.ISO_ZONED_DATE_TIME) },
            nationality = tryOrNull { Locale.forLanguageTag(nat!!) },
            location = location.toEntity(),
        )
    }
}

private fun PictureResponse?.toEntity(): PicturesEntity? = this?.run {
    return if (thumbnail == null && large == null && medium == null) null
    else PicturesEntity(
        thumbnail = thumbnail,
        normal = large ?: medium
    )
}

private fun getContacts(email: String?, phone: String?, cell: String?): ContactsEntity? {
    return if (email == null && phone == null && cell == null) null
    else ContactsEntity(
        email = email,
        phone = phone,
        cell = cell
    )
}

private fun LocationResponse?.toEntity() = this?.run {
    safeLet(street?.number.toString(), street?.name, city, country) { (streetNumber, streetName, city, country) ->
        LocationEntity(
            street = "$streetNumber $streetName",
            city = city,
            state = state,
            country = country,
            postalCode = (postcode as? Double)?.toString() ?: postcode as? String,
            coordinates = safeLet(coordinates?.latitude, coordinates?.longitude) { latitude, longitude ->
                CoordinatesEntity(
                    latitude = latitude,
                    longitude = longitude,
                )
            },
        )
    }
}
