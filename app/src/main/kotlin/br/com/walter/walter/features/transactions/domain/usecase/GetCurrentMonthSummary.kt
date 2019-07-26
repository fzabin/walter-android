package br.com.walter.walter.features.transactions.domain.usecase

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.UseCase
import br.com.walter.walter.core.functional.catching
import br.com.walter.walter.core.functional.getOrThrow
import br.com.walter.walter.core.util.DateFormatter
import br.com.walter.walter.features.transactions.domain.Summary
import br.com.walter.walter.features.transactions.domain.TransactionsRepository
import java.math.BigDecimal

class GetCurrentMonthSummary(
    private val transactionsRepository: TransactionsRepository,
    private val dateFormatter: DateFormatter
) : UseCase<Summary>() {

    override suspend fun run(): Result<Summary> {
        return catching {
            val transactionsWithType = transactionsRepository.getTransactionsBetweenDates(
                startDate = dateFormatter.firstDayOfMonthAsDateFormat(),
                endDate = dateFormatter.lastDayOfMonthAsDateFormat()
            ).getOrThrow()

            val expenses = transactionsWithType.filter { it.isExpense }
            val income = transactionsWithType.filter { it.isIncome }
            val investments = transactionsWithType.filter { it.isInvestment }

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
        }
    }
}