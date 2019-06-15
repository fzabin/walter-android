package br.com.walter.walter.features.transactiontypes.domain

import br.com.walter.walter.core.functional.Result

interface TransactionTypeRepository {

    suspend fun getAll(): Result<List<TransactionType>>

}