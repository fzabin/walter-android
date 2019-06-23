package br.com.walter.walter.features.transactions.presentation

import java.math.BigDecimal

data class TransactionModel(
    val id: Long,
    val uuid: String,
    val value: BigDecimal,
    val date: String,
    val description: String,
    val categoryId: Long
) {

    companion object {
        fun empty() = TransactionModel(0, "", BigDecimal.ZERO, "", "", 0)
    }

    val valueIsNotValid
        get() = value <= BigDecimal.ZERO

    val categoryIsNotValid
        get() = categoryId == 0L

    private val dateIsNotValid
        get() = date.length != 10

    val valid
        get() = !valueIsNotValid
                && !dateIsNotValid
                && description.isNotEmpty()
                && !categoryIsNotValid

}
