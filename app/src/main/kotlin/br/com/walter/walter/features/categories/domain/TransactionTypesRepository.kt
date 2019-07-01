package br.com.walter.walter.features.categories.domain

import br.com.walter.walter.core.functional.Result

interface TransactionTypesRepository {

    suspend fun getAll(): Result<List<TransactionType>>

}