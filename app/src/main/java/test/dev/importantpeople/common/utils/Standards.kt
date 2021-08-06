package test.dev.importantpeople.common.utils

import timber.log.Timber

fun logException(throwable: Throwable) {
    Timber.e(throwable)
}

inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

inline fun <T : Any, R : Any> safeLet(vararg elements: T?, block: (List<T>) -> R?): R? {
    return if (elements.all { it != null }) {
        block(elements.filterNotNull())
    } else null
}

inline fun <T> tryOrNull(function: () -> T): T? = try {
    function()
} catch (e: Exception) {
    null
}