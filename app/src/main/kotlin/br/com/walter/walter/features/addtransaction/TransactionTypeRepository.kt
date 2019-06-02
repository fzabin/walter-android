package br.com.walter.walter.features.addtransaction

import br.com.walter.walter.core.functional.Result

interface TransactionTypeRepository {

    suspend fun getAll(): Result<List<TransactionType>>

}