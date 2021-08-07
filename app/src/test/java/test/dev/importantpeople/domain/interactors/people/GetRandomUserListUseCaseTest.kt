package test.dev.importantpeople.domain.interactors.people

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDateTime
import test.dev.importantpeople.common.FIRST_PAGE
import test.dev.importantpeople.data.remote.response.GetRandomUserListResponse
import test.dev.importantpeople.data.repository.UserRepository
import test.dev.importantpeople.domain.entity.user.*
import test.dev.importantpeople.utils.transformTo
import java.util.*
import kotlin.test.assertEquals

class GetRandomUserListUseCaseTest {
    private lateinit var classUnderTest: GetRandomUserListUseCase
    private var userRepository: UserRepository = mockk()

    @Before
    fun setUp() {
        clearAllMocks()
        classUnderTest = GetRandomUserListUseCase(userRepository)
    }

    @Test
    fun `All data`() {
        runBlocking {
            //GIVEN
            val expectedResult = UserEntity.SUCCESS(data = PEOPLE_DATA)

            coEvery { userRepository.getRandomUserList(FIRST_PAGE) } returns PEOPLE_DATASET

            //WHEN
            val result = classUnderTest.invoke(FIRST_PAGE)

            //THEN
            assertEquals(expectedResult, result)
            coVerify(exactly = 1) {
                userRepository.getRandomUserList(FIRST_PAGE)
            }
        }
    }

    @Test
    fun `Data have a problem (no uuid or title or firstname or lastname or username)`() {
        runBlocking {
            //GIVEN
            val expectedResult = UserEntity.EMPTY_DATA

            coEvery { userRepository.getRandomUserList(FIRST_PAGE) } returns PEOPLE_DATASET_ERROR

            //WHEN
            val result = classUnderTest.invoke(FIRST_PAGE)

            //THEN
            assertEquals(expectedResult, result)
            coVerify(exactly = 1) {
                userRepository.getRandomUserList(FIRST_PAGE)
            }
        }
    }

    @Test
    fun `Empty data`() {
        runBlocking {
            //GIVEN
            val expectedResult = UserEntity.EMPTY_DATA

            coEvery { userRepository.getRandomUserList(FIRST_PAGE) } returns GetRandomUserListResponse(results = emptyList())

            //WHEN
            val result = classUnderTest.invoke(FIRST_PAGE)

            //THEN
            assertEquals(expectedResult, result)
            coVerify(exactly = 1) {
                userRepository.getRandomUserList(FIRST_PAGE)
            }
        }
    }

    @Test
    fun `Null data`() {
        runBlocking {
            //GIVEN
            val expectedResult = UserEntity.ERROR

            coEvery { userRepository.getRandomUserList(FIRST_PAGE) } returns null

            //WHEN
            val result = classUnderTest.invoke(FIRST_PAGE)

            //THEN
            assertEquals(expectedResult, result)
            coVerify(exactly = 1) {
                userRepository.getRandomUserList(FIRST_PAGE)
            }
        }
    }

    val PEOPLE_DATASET = javaClass.classLoader?.transformTo("userListOK.json", GetRandomUserListResponse::class.java)
    val PEOPLE_DATASET_ERROR = javaClass.classLoader?.transformTo("userListKO.json", GetRandomUserListResponse::class.java)
}

val PEOPLE_DATA = listOf(
    UserData(
        uuid = "97890990-7bf2-469d-981c-16a10ae5307f", title = "Mr", firstname = "Karl", lastname = "Johnson", username = "bigpeacock170",
        nationality = Locale("", "US"), gender = Gender.MAN,
        pictures = PicturesEntity(thumbnail = "https://randomuser.me/api/portraits/thumb/men/6.jpg", normal = "https://randomuser.me/api/portraits/men/6.jpg"),
        contacts = ContactsEntity(email = "karl.johnson@example.com", phone = "(068)-320-4900", cell = "(476)-843-3163"),
        birthday = LocalDateTime.parse("1965-11-30T13:18:54.147"), registeredDate = LocalDateTime.parse("2013-03-31T09:43:14.348"),
        location = LocationEntity(
            street = "6057 Avondale Ave", city = "New York", state = "New York", country = "United States", postalCode = "12564",
            coordinates = CoordinatesEntity(latitude = "88.9222", longitude = "-82.9558")
        )
    ),
    UserData(
        uuid = "7b9d7552-a699-4c6d-9e86-d1581be97265", title = "Mr", firstname = "Finn", lastname = "Morris", username = "whitelion863",
        nationality = Locale("", "NZ"), gender = Gender.MAN,
        pictures = PicturesEntity(thumbnail = "https://randomuser.me/api/portraits/thumb/men/64.jpg", normal = "https://randomuser.me/api/portraits/men/64.jpg"),
        contacts = ContactsEntity(email = "finn.morris@example.com", phone = "(727)-069-6763", cell = "(127)-787-1458"),
        birthday = LocalDateTime.parse("1997-12-02T19:57:22.746"), registeredDate = LocalDateTime.parse("2011-08-16T06:54:26.042"),
        location = LocationEntity(
            street = "7130 The Strand", city = "New Plymouth", state = "Nelson", country = "New Zealand", postalCode = "21728",
            coordinates = CoordinatesEntity(latitude = "-87.2603", longitude = "-154.9263")
        )
    ),
    UserData(
        uuid = "446b4f8d-f8ac-44c0-af9c-9533cbdb278d", title = "Miss", firstname = "Romane", lastname = "Perez", username = "brownelephant232",
        nationality = Locale("", "FR"), gender = Gender.WOMAN,
        pictures = PicturesEntity(thumbnail = "https://randomuser.me/api/portraits/thumb/women/36.jpg", normal = "https://randomuser.me/api/portraits/women/36.jpg"),
        contacts = ContactsEntity(email = "romane.perez@example.com", phone = "01-62-65-50-67", cell = "06-16-18-12-63"),
        birthday = LocalDateTime.parse("1995-04-30T08:00:51.990"), registeredDate = LocalDateTime.parse("2002-04-09T16:20:01.048"),
        location = LocationEntity(
            street = "3717 Rue Dubois", city = "Bordeaux", state = "Bouches-du-Rhône", country = "France", postalCode = "48220",
            coordinates = CoordinatesEntity(latitude = "-47.0755", longitude = "-24.7668")
        )
    ),
    UserData(
        uuid = "ec1a5d22-6edb-494a-b5b3-42e050a3a990", title = "Ms", firstname = "Lucia", lastname = "Hammeren", username = "tinykoala697",
        nationality = Locale("", "NO"), gender = Gender.WOMAN,
        pictures = PicturesEntity(thumbnail = "https://randomuser.me/api/portraits/thumb/women/31.jpg", normal = "https://randomuser.me/api/portraits/women/31.jpg"),
        contacts = ContactsEntity(email = "lucia.hammeren@example.com", phone = "50971296", cell = "90562854"),
        birthday = LocalDateTime.parse("1974-06-18T02:40:05.692"), registeredDate = LocalDateTime.parse("2002-05-30T23:54:20.946"),
        location = LocationEntity(
            street = "7767 Tyristubbveien", city = "Torvika", state = "Rogaland", country = "Norway", postalCode = "1514",
            coordinates = CoordinatesEntity(latitude = "4.5291", longitude = "7.5204")
        )
    ),
    UserData(
        uuid = "2a2d24a6-4e82-4444-aa4c-b94cfceda1ed", title = "Mrs", firstname = "Romane", lastname = "Renaud", username = "silvercat363",
        nationality = Locale("", "FR"), gender = Gender.WOMAN,
        pictures = PicturesEntity(thumbnail = "https://randomuser.me/api/portraits/thumb/women/87.jpg", normal = "https://randomuser.me/api/portraits/women/87.jpg"),
        contacts = ContactsEntity(email = "romane.renaud@example.com", phone = "01-43-38-74-92", cell = "06-11-20-06-64"),
        birthday = LocalDateTime.parse("1982-02-15T13:57:45.232"), registeredDate = LocalDateTime.parse("2006-02-04T06:07:35.884"),
        location = LocationEntity(
            street = "6781 Rue Laure-Diebold", city = "Saint-Denis", state = "Doubs", country = "France", postalCode = "93943",
            coordinates = CoordinatesEntity(latitude = "30.4387", longitude = "83.8155")
        )
    )
)