package br.com.walter.walter.features.addtransaction

data class Category (
    val id: Long,
    val description: String,
    val transactionTypeId: Long
)