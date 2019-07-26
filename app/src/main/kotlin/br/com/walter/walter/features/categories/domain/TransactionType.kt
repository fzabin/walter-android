package br.com.walter.walter.features.categories.domain

const val EXPENSE_TYPE_ID = 1L
const val INCOME_TYPE_ID = 2L
const val INVESTMENT_TYPE_ID = 3L

data class TransactionType(
    val id: Long,
    val description: String
)