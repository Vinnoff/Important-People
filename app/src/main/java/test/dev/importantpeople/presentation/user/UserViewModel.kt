package test.dev.importantpeople.presentation.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import test.dev.importantpeople.data.remote.response.ResultResponse
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

    private val _liveDataUserInfo: MutableLiveData<List<ResultResponse>> = MutableLiveData()
    val liveDataUserInfo: LiveData<List<ResultResponse>> get() = _liveDataUserInfo

    init {
        _liveDataNavigation.value = UserNavigation.LIST.toEvent()
        launch {
            _liveDataUserInfo.value = getRandomUserListUseCase.invoke()
        }
    }
}
