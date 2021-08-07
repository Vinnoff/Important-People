package test.dev.importantpeople.presentation.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyObject
import org.mockito.ArgumentMatchers.anyString
import test.dev.importantpeople.common.FIRST_PAGE
import test.dev.importantpeople.domain.entity.user.UserData
import test.dev.importantpeople.domain.entity.user.UserEntity
import test.dev.importantpeople.domain.interactors.people.GetRandomUserListUseCase
import test.dev.importantpeople.presentation.Event
import test.dev.importantpeople.presentation.toEvent
import test.dev.importantpeople.presentation.user.list.UserViewState
import java.util.*

class UserViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val classUnderTest: UserViewModel by lazy { UserViewModel(getRandomUserListUseCase, dispatcher) }

    private val getRandomUserListUseCase: GetRandomUserListUseCase = mockk()

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    private val navigationObserver: Observer<Event<UserNavigation>> = spyk()
    private val userListObserver: Observer<UserViewState> = spyk()
    private val userInfoObserver: Observer<UserData> = spyk()

    @Before
    fun setUp() {
        clearAllMocks()
    }

    @After
    fun finally() {
        classUnderTest.liveDataNavigation.removeObserver(navigationObserver)
        classUnderTest.liveDataUserList.removeObserver(userListObserver)
        classUnderTest.liveDataUserInfo.removeObserver(userInfoObserver)
    }

    @Test
    fun `init ok`() {
        runBlocking {
            //GIVEN
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE) } returns UserEntity.SUCCESS(firstList)

            //WHEN
            classUnderTest.liveDataNavigation.observeForever(navigationObserver)
            classUnderTest.liveDataUserList.observeForever(userListObserver)

            //THEN
            coVerifySequence {
                getRandomUserListUseCase.invoke(FIRST_PAGE)
                navigationObserver.onChanged(UserNavigation.LIST.toEvent())
                userListObserver.onChanged(UserViewState.SUCCESS(firstList))
            }
        }
    }

    @Test
    fun `init ko`() {
        runBlocking {
            //GIVEN
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE) } returns UserEntity.ERROR

            //WHEN
            classUnderTest.liveDataUserList.observeForever(userListObserver)

            //THEN
            coVerifySequence {
                getRandomUserListUseCase.invoke(FIRST_PAGE)
                userListObserver.onChanged(UserViewState.ERROR)
            }
        }
    }

    @Test
    fun `init empty`() {
        runBlocking {
            //GIVEN
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE) } returns UserEntity.EMPTY_DATA

            //WHEN
            classUnderTest.liveDataUserList.observeForever(userListObserver)

            //THEN
            coVerifySequence {
                getRandomUserListUseCase.invoke(FIRST_PAGE)
                userListObserver.onChanged(UserViewState.EMPTY_DATA)
            }
        }
    }

    @Test
    fun `scroll getting good data`() {
        runBlocking {
            //GIVEN
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE) } returns UserEntity.SUCCESS(firstList)
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE + 1) } returns UserEntity.SUCCESS(secondList)

            //WHEN
            classUnderTest.liveDataUserList.observeForever(userListObserver)
            classUnderTest.onListEnded()

            //THEN
            coVerifySequence {
                getRandomUserListUseCase.invoke(FIRST_PAGE)
                userListObserver.onChanged(UserViewState.SUCCESS(firstList))
                userListObserver.onChanged(UserViewState.LOADER)
                getRandomUserListUseCase.invoke(FIRST_PAGE + 1)
                userListObserver.onChanged(UserViewState.SUCCESS(firstList + secondList))
            }
        }
    }

    @Test
    fun `scroll getting bad data`() {
        runBlocking {
            //GIVEN
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE) } returns UserEntity.SUCCESS(firstList)
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE + 1) } returns UserEntity.ERROR

            //WHEN
            classUnderTest.liveDataUserList.observeForever(userListObserver)
            classUnderTest.onListEnded()

            //THEN
            coVerifySequence {
                getRandomUserListUseCase.invoke(FIRST_PAGE)
                userListObserver.onChanged(UserViewState.SUCCESS(firstList))
                userListObserver.onChanged(UserViewState.LOADER)
                getRandomUserListUseCase.invoke(FIRST_PAGE + 1)
                userListObserver.onChanged(UserViewState.ERROR_OLD_DATA(firstList))
            }
        }
    }

    @Test
    fun `scroll getting empty data`() {
        runBlocking {
            //GIVEN
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE) } returns UserEntity.SUCCESS(firstList)
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE + 1) } returns UserEntity.EMPTY_DATA

            //WHEN
            classUnderTest.liveDataUserList.observeForever(userListObserver)
            classUnderTest.onListEnded()

            //THEN
            coVerifySequence {
                getRandomUserListUseCase.invoke(FIRST_PAGE)
                userListObserver.onChanged(UserViewState.SUCCESS(firstList))
                userListObserver.onChanged(UserViewState.LOADER)
                getRandomUserListUseCase.invoke(FIRST_PAGE + 1)
                userListObserver.onChanged(UserViewState.EMPTY_OLD_DATA(firstList))
            }
        }
    }

    @Test
    fun `on user selected`() {
        runBlocking {
            //GIVEN
            val selectedUser = firstList.random()
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE) } returns UserEntity.SUCCESS(firstList)

            //WHEN
            classUnderTest.liveDataNavigation.observeForever(navigationObserver)
            classUnderTest.liveDataUserList.observeForever(userListObserver)
            classUnderTest.liveDataUserInfo.observeForever(userInfoObserver)
            classUnderTest.onUserSelected(selectedUser.uuid)

            //THEN
            coVerifySequence {
                getRandomUserListUseCase.invoke(FIRST_PAGE)
                navigationObserver.onChanged(Event(UserNavigation.LIST))
                userListObserver.onChanged(UserViewState.SUCCESS(firstList))
                navigationObserver.onChanged(Event(UserNavigation.DETAILS))
                userInfoObserver.onChanged(selectedUser)
            }
        }
    }

    @Test
    fun `on click email`() {
        runBlocking {
            //GIVEN
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE) } returns UserEntity.SUCCESS(firstList)

            //WHEN
            classUnderTest.liveDataNavigation.observeForever(navigationObserver)
            classUnderTest.onClickEmail(anyString())

            //THEN
            coVerifySequence {
                navigationObserver.onChanged(Event(UserNavigation.LIST))
                navigationObserver.onChanged(Event(UserNavigation.EMAIL(anyString())))
            }
        }
    }

    @Test
    fun `on click phone`() {
        runBlocking {
            //GIVEN
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE) } returns UserEntity.SUCCESS(firstList)

            //WHEN
            classUnderTest.liveDataNavigation.observeForever(navigationObserver)
            classUnderTest.onClickPhone(anyString())

            //THEN
            coVerifySequence {
                navigationObserver.onChanged(Event(UserNavigation.LIST))
                navigationObserver.onChanged(Event(UserNavigation.PHONE(anyString())))
            }
        }
    }

    @Test
    fun `on click navigation`() {
        runBlocking {
            //GIVEN
            coEvery { getRandomUserListUseCase.invoke(FIRST_PAGE) } returns UserEntity.SUCCESS(firstList)

            //WHEN
            classUnderTest.liveDataNavigation.observeForever(navigationObserver)
            classUnderTest.onClickNavigation(anyString(), anyString(), anyString())

            //THEN
            coVerifySequence {
                navigationObserver.onChanged(Event(UserNavigation.LIST))
                navigationObserver.onChanged(Event(UserNavigation.NAVIGATION(anyString(), anyString(), anyString())))
            }
        }
    }
}

private val firstList = listOf(
    spyk(UserData(UUID.randomUUID().toString(), anyObject(), anyString(), anyString(), anyString(), anyString(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject())),
    spyk(UserData(UUID.randomUUID().toString(), anyObject(), anyString(), anyString(), anyString(), anyString(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject())),
    spyk(UserData(UUID.randomUUID().toString(), anyObject(), anyString(), anyString(), anyString(), anyString(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject())),
    spyk(UserData(UUID.randomUUID().toString(), anyObject(), anyString(), anyString(), anyString(), anyString(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject())),
)
private val secondList = listOf(
    spyk(UserData(UUID.randomUUID().toString(), anyObject(), anyString(), anyString(), anyString(), anyString(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject())),
    spyk(UserData(UUID.randomUUID().toString(), anyObject(), anyString(), anyString(), anyString(), anyString(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject())),
    spyk(UserData(UUID.randomUUID().toString(), anyObject(), anyString(), anyString(), anyString(), anyString(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject())),
    spyk(UserData(UUID.randomUUID().toString(), anyObject(), anyString(), anyString(), anyString(), anyString(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject(), anyObject())),
)