package test.dev.importantpeople.presentation.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import test.dev.importantpeople.domain.entity.user.UserEntity
import test.dev.importantpeople.domain.interactors.people.GetRandomUserListUseCase
import test.dev.importantpeople.presentation.BaseViewModel
import test.dev.importantpeople.presentation.Event
import test.dev.importantpeople.presentation.toEvent

class UserViewModel(
    private val getRandomUserListUseCase: GetRandomUserListUseCase,
    dispatcher: CoroutineDispatcher
) : BaseViewModel(dispatcher) {

    private val _liveDataNavigation: MutableLiveData<Event<UserNavigation>> = MutableLiveData()
    val liveDataNavigation: LiveData<Event<UserNavigation>> get() = _liveDataNavigation

    private val _liveDataUserList: MutableLiveData<List<UserEntity>> = MutableLiveData()
    val liveDataUserList: LiveData<List<UserEntity>> get() = _liveDataUserList
    private val _liveDataUserInfo: MutableLiveData<UserEntity> = MutableLiveData()
    val liveDataUserInfo: LiveData<UserEntity> get() = _liveDataUserInfo

    init {
        _liveDataNavigation.value = UserNavigation.LIST.toEvent()
        launch {
            _liveDataUserList.value = getRandomUserListUseCase.invoke()
        }
    }

    fun onUserSelected(uuid: String) {
        _liveDataNavigation.value = UserNavigation.DETAILS.toEvent()
        _liveDataUserInfo.value = liveDataUserList.value?.find { it.uuid == uuid }
    }

    fun onClickPhone(phone: String) {
        _liveDataNavigation.value = UserNavigation.PHONE(phone).toEvent()
    }

    fun onClickEmail(email: String) {
        _liveDataNavigation.value = UserNavigation.EMAIL(email).toEvent()
    }

    fun onClickNavigation(username: String, latitude: String, longitude: String) {
        _liveDataNavigation.value = UserNavigation.NAVIGATION(username, latitude, longitude).toEvent()
    }
}
