package br.com.walter.walter.features.home.presentation

import androidx.lifecycle.MutableLiveData
import br.com.walter.walter.core.archcomponents.BaseViewModel
import br.com.walter.walter.core.functional.onFailure
import br.com.walter.walter.core.functional.onSuccess
import br.com.walter.walter.core.util.DateFormatter
import br.com.walter.walter.features.transactions.domain.TransactionsRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal

class HomeViewModel(
    private val transactionsRepository: TransactionsRepository,
    private val dateFormatter: DateFormatter
) : BaseViewModel() {

    val totalExpenses by lazy { MutableLiveData<BigDecimal>() }
    val totalIncome by lazy { MutableLiveData<BigDecimal>() }
    val totalInvestments by lazy { MutableLiveData<BigDecimal>() }
    val balance by lazy { MutableLiveData<BigDecimal>() }

    fun getMonthlySummary() {
        launch {
            transactionsRepository.getMonthlySummary(
                startDate = dateFormatter.firstDayOfMonthAsDateFormat(),
                endDate = dateFormatter.lastDayOfMonthAsDateFormat()
            )
                .onSuccess {
                    totalExpenses.postValue(it.totalExpenses)
                    totalIncome.postValue(it.totalInvestment)
                    totalInvestments.postValue(it.totalInvestment)
                    balance.postValue(it.balance)
                }
                .onFailure {

                }
        }
    }

}