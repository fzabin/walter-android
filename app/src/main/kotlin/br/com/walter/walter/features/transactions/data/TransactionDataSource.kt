package br.com.walter.walter.features.transactions.data

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.mapCatching
import br.com.walter.walter.core.functional.resultFrom
import br.com.walter.walter.features.transactions.Transaction
import br.com.walter.walter.features.transactions.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TransactionDataSource(
    private val transactionDao: TransactionDao,
    private val transactionDtoMapper: TransactionDtoMapper
): TransactionRepository {

    override suspend fun save(transaction: Transaction): Result<Unit> = withContext(Dispatchers.IO) {
        resultFrom {
            transactionDao.save(transactionDtoMapper.mapReverse(transaction))
        }.mapCatching { }
    }



}