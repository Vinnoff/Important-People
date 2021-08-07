package test.dev.importantpeople.data.remote.response

data class GetRandomUserListResponse(
    val results: List<ResultResponse>?,
)

data class ResultResponse(
    val gender: String?,
    val name: NameResponse?,
    val location: LocationResponse?,
    val email: String?,
    val login: LoginResponse?,
    val dob: DateResponse?,
    val registered: DateResponse?,
    val phone: String?,
    val cell: String?,
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
)

data class LoginResponse(
    val uuid: String?,
    val username: String?,
)

data class DateResponse(
    val date: String?,
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