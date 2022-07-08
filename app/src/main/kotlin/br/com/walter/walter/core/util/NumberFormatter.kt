package br.com.walter.walter.core.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

private const val LANGUAGE_PT = "pt"
private const val COUNTRY_BR = "br"

class NumberFormatter {
    fun getCurrencyFormat(value: BigDecimal): String {
        return NumberFormat.getCurrencyInstance(Locale(LANGUAGE_PT, COUNTRY_BR)).format(value)
    }
}