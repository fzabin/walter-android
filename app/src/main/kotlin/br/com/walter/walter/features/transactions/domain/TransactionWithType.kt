package br.com.walter.walter.features.transactions.domain

import br.com.walter.walter.features.categories.domain.EXPENSE_TYPE_ID
import br.com.walter.walter.features.categories.domain.INCOME_TYPE_ID
import br.com.walter.walter.features.categories.domain.INVESTMENT_TYPE_ID
import java.math.BigDecimal

data class TransactionWithType(
    val id: Long,
    val uuid: String,
    val value: BigDecimal,
    val date: String,
    val description: String,
    val categoryId: Long,
    val transactionTypeId: Long
) {

    val isExpense: Boolean
        get() = transactionTypeId == EXPENSE_TYPE_ID

    val isIncome: Boolean
        get() = transactionTypeId == INCOME_TYPE_ID

    val isInvestment: Boolean
        get() = transactionTypeId == INVESTMENT_TYPE_ID

}