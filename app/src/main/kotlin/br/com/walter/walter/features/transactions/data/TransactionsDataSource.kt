package br.com.walter.walter.features.transactions.data

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.mapCatching
import br.com.walter.walter.core.functional.resultFrom
import br.com.walter.walter.features.transactions.domain.Transaction
import br.com.walter.walter.features.transactions.domain.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionsDataSource(
    private val transactionsDao: TransactionsDao,
    private val transactionDtoMapper: TransactionDtoMapper
): TransactionsRepository {

    override suspend fun save(transaction: Transaction): Result<Unit> = withContext(Dispatchers.IO) {
        resultFrom {
            transactionsDao.save(transactionDtoMapper.mapReverse(transaction))
        }.mapCatching { }
    }



}