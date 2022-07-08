package br.com.walter.walter.features.transactions.domain.usecase

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.UseCase
import br.com.walter.walter.core.functional.mapCatching
import br.com.walter.walter.core.util.DateFormatter
import br.com.walter.walter.features.transactions.domain.TransactionWithType
import br.com.walter.walter.features.transactions.domain.TransactionsRepository

class GetCurrentMonthExpences(
    private val transactionsRepository: TransactionsRepository,
    private val dateFormatter: DateFormatter
) : UseCase<List<TransactionWithType>>() {

    override suspend fun run(): Result<List<TransactionWithType>> {
        return transactionsRepository.getTransactionsBetweenDates(
            startDate = dateFormatter.firstDayOfMonthAsDateFormat(),
            endDate = dateFormatter.lastDayOfMonthAsDateFormat()
        ).mapCatching { transactionsWithType ->
            transactionsWithType.filter { it.isExpense }
        }
    }
}