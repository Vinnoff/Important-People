package test.dev.importantpeople.common.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*


fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this)
}

fun LocalDateTime.print(format: DateTimeFormat, locale: Locale? = Locale.getDefault()): String =
    DateTimeFormatter.ofPattern(format.format, locale).format(this)

enum class DateTimeFormat(val format: String) {
    FMT_DATE_TIME("dd/MM - HH:mm"),
    FMT_TIME("HH:mm"),
}