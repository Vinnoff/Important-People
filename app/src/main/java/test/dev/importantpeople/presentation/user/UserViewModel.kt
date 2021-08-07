package test.dev.importantpeople.presentation.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import test.dev.importantpeople.common.FIRST_PAGE
import test.dev.importantpeople.common.utils.CustomCoroutineExceptionHandler
import test.dev.importantpeople.domain.entity.user.UserData
import test.dev.importantpeople.domain.entity.user.UserEntity
import test.dev.importantpeople.domain.interactors.people.GetRandomUserListUseCase
import test.dev.importantpeople.presentation.BaseViewModel
import test.dev.importantpeople.presentation.Event
import test.dev.importantpeople.presentation.toEvent
import test.dev.importantpeople.presentation.user.list.UserViewState

class UserViewModel(
    private val getRandomUserListUseCase: GetRandomUserListUseCase,
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    private val _liveDataNavigation: MutableLiveData<Event<UserNavigation>> = MutableLiveData()
    val liveDataNavigation: LiveData<Event<UserNavigation>> get() = _liveDataNavigation

    private val _liveDataUserList: MutableLiveData<UserViewState> = MutableLiveData()
    val liveDataUserList: LiveData<UserViewState> get() = _liveDataUserList
    private val userList get() = (liveDataUserList.value as? UserViewState.SUCCESS)?.data
    private val _liveDataUserInfo: MutableLiveData<UserData> = MutableLiveData()
    val liveDataUserInfo: LiveData<UserData> get() = _liveDataUserInfo

    private var page = FIRST_PAGE

    init {
        _liveDataNavigation.value = UserNavigation.LIST.toEvent()
        getUserList(page)
    }

    fun onUserSelected(uuid: String) {
        _liveDataNavigation.value = UserNavigation.DETAILS.toEvent()
        _liveDataUserInfo.value = userList?.find { it.uuid == uuid }
    }

    fun onClickPhone(phone: String) {
        _liveDataNavigation.value = UserNavigation.PHONE(phone).toEvent()
    }

    fun onClickEmail(email: String) {
        _liveDataNavigation.value = UserNavigation.EMAIL(email).toEvent()
    }

    fun onClickNavigation(street: String, city: String, country: String) {
        _liveDataNavigation.value = UserNavigation.NAVIGATION(street, city, country).toEvent()
    }

    fun onListEnded() {
        if (_liveDataUserList.value is UserViewState.LOADER) return
        getUserList(++page)
    }

    private fun getUserList(pagination: Int) {
        val userList = userList
        _liveDataUserList.value = UserViewState.LOADER
        launch(CustomCoroutineExceptionHandler { _liveDataUserList.value = UserViewState.ERROR }) {
            _liveDataUserList.value = getRandomUserListUseCase.invoke(pagination).toViewState(userList)
        }
    }

    private fun UserEntity.toViewState(userList: List<UserData>?): UserViewState {
        return when (this) {
            is UserEntity.EMPTY_DATA -> if (userList != null) UserViewState.EMPTY_OLD_DATA(userList) else UserViewState.EMPTY_DATA
            is UserEntity.ERROR -> if (userList != null) UserViewState.ERROR_OLD_DATA(userList) else UserViewState.ERROR
            is UserEntity.SUCCESS -> UserViewState.SUCCESS(userList?.plus(data) ?: data)
        }
    }
}
