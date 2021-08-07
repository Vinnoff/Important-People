package test.dev.importantpeople.data.repository

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import test.dev.importantpeople.common.FIRST_PAGE
import test.dev.importantpeople.data.remote.response.GetRandomUserListResponse
import test.dev.importantpeople.data.remote.source.RemoteDataSource
import kotlin.test.assertEquals

class UserRepositoryImplTest {
    private val datasource: RemoteDataSource = mockk()

    private val classUnderTest = UserRepositoryImpl(datasource)

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `randomUser returns a result`() {
        runBlocking {
            //GIVEN
            val response = spyk(GetRandomUserListResponse(ArgumentMatchers.any()))
            coEvery { datasource.getRandomUserList(FIRST_PAGE) } returns response

            //WHEN
            val result = classUnderTest.getRandomUserList(FIRST_PAGE)

            //THEN
            assertEquals(response, result)
            coVerify(exactly = 1) {
                datasource.getRandomUserList(FIRST_PAGE)
            }
            confirmVerified(datasource)
        }
    }

    @Test
    fun `randomUser returns null`() {
        runBlocking {
            //GIVEN
            val response = null
            coEvery { datasource.getRandomUserList(FIRST_PAGE) } returns response

            //WHEN
            val result = classUnderTest.getRandomUserList(FIRST_PAGE)

            //THEN
            assertEquals(response, result)
            coVerify(exactly = 1) {
                datasource.getRandomUserList(FIRST_PAGE)
            }
            confirmVerified(datasource)
        }
    }
}