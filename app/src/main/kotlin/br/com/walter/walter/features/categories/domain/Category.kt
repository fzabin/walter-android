package br.com.walter.walter.features.categories.domain

data class Category (
    val id: Long,
    val description: String,
    val transactionTypeId: Long
)