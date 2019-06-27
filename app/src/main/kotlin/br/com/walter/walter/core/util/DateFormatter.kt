package br.com.walter.walter.core.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

private const val FORMAT_TZ = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val FORMAT_DATE = "yyyy-MM-dd"
private const val FORMAT_DISPLAY = "dd/MM/yy, HH:mm"
private const val FORMAT_DISPLAY_BR = "dd/MM/yyyy"

class DateFormatter {

    private val dtfTZ by lazy { DateTimeFormat.forPattern(FORMAT_TZ) }
    private val dtfDate by lazy { DateTimeFormat.forPattern(FORMAT_DATE) }
    private val dtfDisplay by lazy { DateTimeFormat.forPattern(FORMAT_DISPLAY) }
    private val dtfDisplayBr by lazy { DateTimeFormat.forPattern(FORMAT_DISPLAY_BR) }

    fun tzFormatToDisplayFormat(input: String): String =
        dtfDisplay.print(dtfTZ.parseDateTime(input).toDateTime())

    fun tzFormatToDisplayBrFormat(input: String): String =
        dtfDisplayBr.print(dtfDate.parseDateTime(input).toDateTime())

    fun nowAsTzFormat(): String = dtfTZ.print(DateTime())

    fun nowAsBrFormat(): String = dtfDisplayBr.print(DateTime())
}