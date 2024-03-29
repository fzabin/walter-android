package br.com.walter.walter.features.transactions.domain

import java.math.BigDecimal

data class Transaction(
    val id: Long,
    val uuid: String,
    val value: BigDecimal,
    val date: String,
    val description: String,
    val categoryId: Long
)