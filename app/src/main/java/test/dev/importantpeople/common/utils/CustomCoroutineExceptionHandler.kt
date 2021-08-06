package test.dev.importantpeople.common.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

open class CustomCoroutineExceptionHandler(val handler: () -> Unit) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        handler()
        genericExceptionHandle(exception)
    }
}

private fun genericExceptionHandle(exception: Throwable) {
    logException(exception)
}