package br.com.walter.walter.features.addtransaction.presentation

import br.com.walter.walter.core.platform.CoroutinePresenter
import br.com.walter.walter.core.functional.onFailure
import br.com.walter.walter.core.functional.onSuccess
import br.com.walter.walter.core.util.DateFormatter
import br.com.walter.walter.features.addtransaction.CategoryRepository
import kotlinx.coroutines.launch

class AddTransactionPresenter(
    private val view: AddTransactionContract.View,
    private val categoryRepository: CategoryRepository
) : AddTransactionContract.Presenter, CoroutinePresenter() {

    override fun start() {
        getAllCategories()
        setDefaultDate()
    }

    override fun getAllCategories() {
        launch {
            categoryRepository.getAll()
                .onSuccess { }
                .onFailure { view.showMessage("Failure!") }
        }
    }

    override fun onDateSelected(date: String) {
        view.setDateField(date)
    }

    override fun setDefaultDate() {
        view.setDateField(DateFormatter().nowAsBrFormat())
    }
}