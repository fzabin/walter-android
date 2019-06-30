package br.com.walter.walter.features.transactions.domain

import java.math.BigDecimal

data class Summary(
    val totalExpenses: BigDecimal,
    val totalIncome: BigDecimal,
    val totalInvestment: BigDecimal,
    val balance: BigDecimal
)