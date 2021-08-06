package test.dev.importantpeople.data.remote.response

data class GetRandomUserListResponse(
    val results: List<ResultResponse>?,
    val info: InfoResponse?,
)

data class ResultResponse(
    val gender: String?,
    val name: NameResponse?,
    val location: LocationResponse?,
    val email: String?,
    val login: LoginResponse?,
    val dob: DobResponse?,
    val registered: DobResponse?,
    val phone: String?,
    val cell: String?,
    val id: IdResponse?,
    val picture: PictureResponse?,
    val nat: String?,
)

data class NameResponse(
    val title: String?,
    val first: String?,
    val last: String?,
)

data class LocationResponse(
    val street: StreetResponse?,
    val city: String?,
    val state: String?,
    val country: String?,
    val postcode: Any?,
    val coordinates: CoordinatesResponse?,
    val timezone: TimezoneResponse?,
)

data class LoginResponse(
    val uuid: String?,
    val username: String?,
    val password: String?,
    val salt: String?,
    val md5: String?,
    val sha1: String?,
    val sha256: String?,
)

data class DobResponse(
    val date: String?,
    val age: Int?,
)

data class RegisteredResponse(
    val date: String?,
    val age: Int?,
)

data class IdResponse(
    val name: String?,
    val value: String?,
)

data class PictureResponse(
    val large: String?,
    val medium: String?,
    val thumbnail: String?,
)

data class StreetResponse(
    val number: Int?,
    val name: String?,
)

data class CoordinatesResponse(
    val latitude: String?,
    val longitude: String?,
)

data class TimezoneResponse(
    val offset: String?,
    val description: String?,
)

data class InfoResponse(
    val seed: String?,
    val results: Int?,
    val page: Int?,
    val version: String?,
)