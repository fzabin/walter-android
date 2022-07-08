package br.com.walter.walter.features.transactions.domain.usecase

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.UseCaseWithParams
import br.com.walter.walter.features.transactions.domain.Transaction
import br.com.walter.walter.features.transactions.domain.TransactionsRepository

class AddTransaction(
    private val transactionsRepository: TransactionsRepository
) : UseCaseWithParams<Unit, AddTransaction.Params>() {

    override suspend fun run(params: Params): Result<Unit> {
        return transactionsRepository.save(params.transaction)
    }

    data class Params(
        val transaction: Transaction
    )
}