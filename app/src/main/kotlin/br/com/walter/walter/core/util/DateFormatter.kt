package br.com.walter.walter.core.util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

private const val FORMAT_TZ = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val FORMAT_DISPLAY = "dd/MM/yy, HH:mm"
private const val FORMAT_BR = "dd/MM/yyyy"

class DateFormatter {

    private val dtfTZ by lazy { DateTimeFormat.forPattern(FORMAT_TZ) }
    private val dtfDisplay by lazy { DateTimeFormat.forPattern(FORMAT_DISPLAY) }
    private val dtfBR by lazy { DateTimeFormat.forPattern(FORMAT_BR) }

    fun tzFormatToDisplayFormat(input: String): String =
        dtfDisplay.print(dtfTZ.parseDateTime(input).toDateTime())

    fun nowAsTzFormat(): String = dtfTZ.print(DateTime())

    fun nowAsBrFormat(): String = dtfBR.print(DateTime())
}