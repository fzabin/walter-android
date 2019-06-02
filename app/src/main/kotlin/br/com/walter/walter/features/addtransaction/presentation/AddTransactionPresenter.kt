package br.com.walter.walter.features.addtransaction.presentation

import br.com.walter.walter.core.platform.CoroutinePresenter
import br.com.walter.walter.core.functional.onFailure
import br.com.walter.walter.core.functional.onSuccess
import br.com.walter.walter.features.addtransaction.TransactionTypeRepository
import kotlinx.coroutines.launch

class AddTransactionPresenter(
    private val view: AddTransactionContract.View,
    private val repository: TransactionTypeRepository
): AddTransactionContract.Presenter, CoroutinePresenter() {

    override fun start() {
        getAllTransactionTypes()
    }

    override fun getAllTransactionTypes() {
        launch {
            repository.getAll()
                .onSuccess { transactionTypes ->
                    view.showMessage(transactionTypes.toString())
                }
                .onFailure { view.showMessage("Failure!") }
        }
    }

}