package br.com.walter.walter.features.transactions.domain

import br.com.walter.walter.core.functional.Result

interface TransactionsRepository {

    suspend fun save(transaction: Transaction): Result<Unit>

}