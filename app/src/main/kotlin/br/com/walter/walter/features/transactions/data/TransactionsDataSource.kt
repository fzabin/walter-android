package br.com.walter.walter.features.transactions.data

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.mapCatching
import br.com.walter.walter.core.functional.resultFrom
import br.com.walter.walter.features.transactions.domain.Transaction
import br.com.walter.walter.features.transactions.domain.TransactionsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TransactionsDataSource(
    private val transactionsDao: TransactionsDao,
    private val transactionDtoMapper: TransactionDtoMapper
) : TransactionsRepository {

    override suspend fun save(transaction: Transaction): Result<Unit> = withContext(IO) {
        resultFrom {
            transactionsDao.save(transactionDtoMapper.mapReverse(transaction))
        }.mapCatching { }
    }

    override suspend fun getMontlyTransactions(
        startDate: String,
        endDate: String
    ): Result<List<Transaction>> = withContext(IO) {
        resultFrom {
            transactionsDao.getTransactionsBetweenDates(startDate, endDate)
        }.mapCatching { transactionDtoMapper.mapList(it) }
    }
}