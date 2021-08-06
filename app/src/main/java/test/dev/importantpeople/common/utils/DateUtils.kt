package test.dev.importantpeople.common.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

fun String.toLocalDateTime(format: DateTimeFormatter): LocalDateTime {
    return LocalDateTime.parse(this, format)
}

fun LocalDateTime.print(format: DateTimeFormatter): String =
    format.format(this)