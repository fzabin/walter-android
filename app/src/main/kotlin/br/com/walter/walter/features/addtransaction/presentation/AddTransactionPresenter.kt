package br.com.walter.walter.features.addtransaction.presentation

import br.com.walter.walter.core.platform.CoroutinePresenter
import br.com.walter.walter.core.functional.onFailure
import br.com.walter.walter.core.functional.onSuccess
import br.com.walter.walter.features.addtransaction.CategoryRepository
import br.com.walter.walter.features.addtransaction.TransactionTypeRepository
import kotlinx.coroutines.launch

class AddTransactionPresenter(
    private val view: AddTransactionContract.View,
    private val categoryRepository: CategoryRepository
) : AddTransactionContract.Presenter, CoroutinePresenter() {

    override fun start() {
        getAllCategories()
    }

    override fun getAllCategories() {
        launch {
            categoryRepository.getAll()
                .onSuccess { categories ->
                    view.showMessage(categories.toString())
                }
                .onFailure { view.showMessage("Failure!") }
        }
    }

}