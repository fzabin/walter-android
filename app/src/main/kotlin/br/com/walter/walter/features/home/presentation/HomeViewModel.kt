package br.com.walter.walter.features.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.walter.walter.core.archcomponents.BaseViewModel
import br.com.walter.walter.core.functional.onFailure
import br.com.walter.walter.core.functional.onSuccess
import br.com.walter.walter.features.transactions.domain.Summary
import br.com.walter.walter.features.transactions.domain.usecase.GetCurrentMonthSummary
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCurrentMonthSummary: GetCurrentMonthSummary
) : BaseViewModel() {

    val summary: LiveData<Summary> get() = _summary
    private val _summary = MutableLiveData<Summary>()

    fun launchGetCurrentMonthSummary() {
        launch {
            getCurrentMonthSummary()
                .onSuccess { _summary.value = it }
                .onFailure {}
        }
    }

}