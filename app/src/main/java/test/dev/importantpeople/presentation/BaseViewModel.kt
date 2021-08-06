package test.dev.importantpeople.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import test.dev.importantpeople.common.utils.logException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(
    private val dispatcher: CoroutineDispatcher
) : ViewModel(), CoroutineScope {

    private var handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable -> baseHandleException(throwable) }

    private var job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job + handler


    override fun onCleared() {
        super.onCleared()
        job.cancelChildren() // Cancel job on activity destroy. After destroy all children jobs will be cancelled automatically
    }


    private fun baseHandleException(throwable: Throwable) {
        logException(throwable)
    }
}