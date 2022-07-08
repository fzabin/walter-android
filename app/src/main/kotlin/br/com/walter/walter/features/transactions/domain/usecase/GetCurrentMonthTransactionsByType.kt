package br.com.walter.walter.features.transactions.domain.usecase

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.UseCaseWithParams
import br.com.walter.walter.core.functional.mapCatching
import br.com.walter.walter.core.util.DateFormatter
import br.com.walter.walter.features.categories.domain.EXPENSE_TYPE_ID
import br.com.walter.walter.features.categories.domain.INCOME_TYPE_ID
import br.com.walter.walter.features.categories.domain.INVESTMENT_TYPE_ID
import br.com.walter.walter.features.transactions.domain.TransactionWithType
import br.com.walter.walter.features.transactions.domain.TransactionsRepository

class GetCurrentMonthTransactionsByType(
    private val transactionsRepository: TransactionsRepository,
    private val dateFormatter: DateFormatter
) : UseCaseWithParams<List<TransactionWithType>, GetCurrentMonthTransactionsByType.Params>() {

    override suspend fun run(params: Params): Result<List<TransactionWithType>> {
        return transactionsRepository.getTransactionsBetweenDates(
            startDate = dateFormatter.firstDayOfMonthAsDateFormat(),
            endDate = dateFormatter.lastDayOfMonthAsDateFormat()
        ).mapCatching { transactionsWithType ->
            when (params.transactionTypeId) {
                EXPENSE_TYPE_ID -> {
                    transactionsWithType.filter { it.isExpense }
                }
                INCOME_TYPE_ID -> {
                    transactionsWithType.filter { it.isIncome }
                }
                INVESTMENT_TYPE_ID -> {
                    transactionsWithType.filter { it.isInvestment }
                }
                else -> {
                    transactionsWithType
                }
            }
        }
    }

    data class Params(
        val transactionTypeId: Long
    )
}