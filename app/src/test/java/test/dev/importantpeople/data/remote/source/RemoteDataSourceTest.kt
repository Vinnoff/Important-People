package test.dev.importantpeople.data.remote.source

import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import retrofit2.Response
import test.dev.importantpeople.common.FIRST_PAGE
import test.dev.importantpeople.data.remote.RandomUserService
import test.dev.importantpeople.data.remote.response.GetRandomUserListResponse
import kotlin.test.assertEquals

class RemoteDataSourceTest {
    private val randomUserService: RandomUserService = mockk()
    private val classUnderTest = RemoteDataSource(randomUserService)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    //************************************
    //  getRandomUserList()
    //************************************
    // region
    @Test
    fun `randomUser (success 200)`() {
        runBlocking {
            // GIVEN
            val response = spyk(GetRandomUserListResponse(ArgumentMatchers.any()))

            coEvery { randomUserService.getRandomUserList(FIRST_PAGE) } returns Response.success(response)

            // WHEN
            val result = classUnderTest.getRandomUserList(FIRST_PAGE)

            //THEN
            assertEquals(response, result)
            coVerify(exactly = 1) {
                randomUserService.getRandomUserList(FIRST_PAGE)
            }
        }
    }

    @Test
    fun `randomUser (error 500)`() {
        runBlocking {
            // GIVEN
            coEvery { randomUserService.getRandomUserList(FIRST_PAGE) } returns Response.error(500, ResponseBody.create(MediaType.parse("application/json"), "{}"))

            // WHEN
            val result = classUnderTest.getRandomUserList(FIRST_PAGE)

            //THEN
            assertEquals(null, result)
            coVerify(exactly = 1) {
                randomUserService.getRandomUserList(FIRST_PAGE)
            }
        }
    }
    // endregion
}