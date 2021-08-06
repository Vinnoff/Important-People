package test.dev.importantpeople.common.utils

import timber.log.Timber

fun logException(throwable: Throwable) {
    Timber.e(throwable)
}