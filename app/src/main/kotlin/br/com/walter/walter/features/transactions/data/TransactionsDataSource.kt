package br.com.walter.walter.features.transactions.data

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.mapCatching
import br.com.walter.walter.core.functional.resultFrom
import br.com.walter.walter.features.transactions.domain.Summary
import br.com.walter.walter.features.transactions.domain.Transaction
import br.com.walter.walter.features.transactions.domain.TransactionWithType
import br.com.walter.walter.features.transactions.domain.TransactionsRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class TransactionsDataSource(
    private val transactionsDao: TransactionsDao,
    private val transactionDtoMapper: TransactionDtoMapper,
    private val transactionWithTypeDtoMapper: TransactionWithTypeDtoMapper
) : TransactionsRepository {

    override suspend fun save(transaction: Transaction): Result<Unit> = withContext(IO) {
        resultFrom {
            transactionsDao.save(transactionDtoMapper.mapReverse(transaction))
        }.mapCatching { }
    }

    override suspend fun getMonthlyTransactions(
        startDate: String,
        endDate: String
    ): Result<List<TransactionWithType>> = withContext(IO) {
        resultFrom {
            transactionsDao.getTransactionsBetweenDates(startDate, endDate)
        }.mapCatching { transactionWithTypeDtoMapper.mapList(it) }
    }

    override suspend fun getMonthlySummary(
        startDate: String,
        endDate: String
    ): Result<Summary> = withContext(IO) {
        resultFrom {
            val transactionsWithTypeDto = transactionsDao.getTransactionsBetweenDates(startDate, endDate)
            val transactionsWithType = transactionWithTypeDtoMapper.mapList(transactionsWithTypeDto)

            val expenses = transactionsWithType.filter { it.transactionTypeId == 1L }
            val income = transactionsWithType.filter { it.transactionTypeId == 2L }
            val investments = transactionsWithType.filter { it.transactionTypeId == 3L }

            val totalExpenses = expenses.fold(BigDecimal.ZERO) { acc, transaction -> acc + transaction.value }
            val totalIncome = income.fold(BigDecimal.ZERO) { acc, transaction -> acc + transaction.value }
            val totalInvestments = investments.fold(BigDecimal.ZERO) { acc, transaction -> acc + transaction.value }

            val balance = totalIncome - totalExpenses + totalInvestments

            Summary(
                totalExpenses = totalExpenses,
                totalIncome = totalIncome,
                totalInvestment = totalInvestments,
                balance = balance
            )
        }.mapCatching { it }
    }
}