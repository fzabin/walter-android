package br.com.walter.walter.features.transactions.presentation

import br.com.walter.walter.R
import br.com.walter.walter.core.platform.CoroutinePresenter
import br.com.walter.walter.core.functional.onFailure
import br.com.walter.walter.core.functional.onSuccess
import br.com.walter.walter.core.provider.ResourceProvider
import br.com.walter.walter.core.util.DateFormatter
import br.com.walter.walter.features.categories.domain.Category
import br.com.walter.walter.features.categories.domain.CategoriesRepository
import br.com.walter.walter.features.transactions.domain.TransactionsRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal

const val EXPENSE_TYPE_ID = 1L
const val INCOME_TYPE_ID = 2L
const val INVESTMENT_TYPE_ID = 3L

class AddTransactionPresenter(
    private val view: AddTransactionContract.View,
    private val categoriesRepository: CategoriesRepository,
    private val transactionsRepository: TransactionsRepository,
    private val transactionModelMapper: TransactionModelMapper,
    private val dateFormatter: DateFormatter,
    private val resourceProvider: ResourceProvider
) : AddTransactionContract.Presenter, CoroutinePresenter() {

    private var categories: List<Category>? = null
    private var expenseCategories: List<Category>? = null
    private var incomeCategories: List<Category>? = null
    private var investmentCategories: List<Category>? = null

    private var transaction = TransactionModel.empty()

    override fun start() {
        getAllCategories()
        setDefaultDate()
    }

    override fun getAllCategories() {
        launch {
            categoriesRepository.getAll()
                .onSuccess { allCategories ->
                    categories = allCategories.filter { it.transactionTypeId == EXPENSE_TYPE_ID }
                    expenseCategories = allCategories.filter { it.transactionTypeId == EXPENSE_TYPE_ID }
                    incomeCategories = allCategories.filter { it.transactionTypeId == INCOME_TYPE_ID }
                    investmentCategories = allCategories.filter { it.transactionTypeId == INVESTMENT_TYPE_ID }
                }
                .onFailure { view.showMessage("Failure!") }
        }
    }

    override fun filterCategories(transactionTypeId: Long) {
        when (transactionTypeId) {
            EXPENSE_TYPE_ID -> categories = expenseCategories
            INCOME_TYPE_ID -> categories = incomeCategories
            INVESTMENT_TYPE_ID -> categories = investmentCategories
        }

        resetCategoryField()
    }

    private fun resetCategoryField() {
        view.setCategoryField("")
        transaction = transaction.copy(categoryId = 0L)
    }

    override fun onDateSelected(date: String) {
        setDate(date)
        validateDate()
        view.setDateField(dateFormatter.tzFormatToDisplayBrFormat(date))
    }

    private fun setDate(date: String) {
        transaction = transaction.copy(date = date)
    }

    override fun setDefaultDate() {
        setDate(dateFormatter.nowAsDateFormat())
        view.setDateField(DateFormatter().nowAsBrFormat())
    }

    override fun showCategoryDialog() {
        view.showCategoryDialog(categories!!)
    }

    override fun onCategorySelected(category: Category) {
        transaction = transaction.copy(categoryId = category.id)
        view.setCategoryField(category.description)
        validateCategory()
    }

    override fun handleValueField(value: String) {
        setValue(value)
        validateValue()
    }

    private fun setValue(newValue: String) {
        transaction =
            if (newValue.isEmpty()) {
                transaction.copy(value = BigDecimal.ZERO)
            } else {
                transaction.copy(value = newValue.toBigDecimal())
            }
    }

    private fun validateValue() {
        when {
            transaction.valueIsNotValid -> {
                view.handleInvalidValueError(resourceProvider.getString(R.string.addtransaction_value_validation_error))
            }
            else -> {
                view.handleInvalidValueError("")
            }
        }
    }

    private fun validateCategory() {
        when {
            transaction.categoryIsNotValid -> {
                view.handleInvalidCategoryError(resourceProvider.getString(R.string.addtransaction_category_validation_error))
            }
            else -> {
                view.handleInvalidCategoryError("")
            }
        }
    }

    private fun validateDate() {
        when {
            transaction.date.isEmpty() -> {
                view.handleInvalidDateError(resourceProvider.getString(R.string.addtransaction_date_validation_error))
            }
            else -> {
                view.handleInvalidDateError("")
            }
        }
    }

    override fun handleDescriptionField(newDescription: String) {
        setDescription(newDescription)
        validateDescription()
    }

    private fun setDescription(newDescription: String) {
        transaction =
            if (newDescription.isEmpty()) {
                transaction.copy(description = "")
            } else {
                transaction.copy(description = newDescription)
            }
    }

    private fun validateDescription() {
        when {
            transaction.description.isEmpty() -> {
                view.handleInvalidDescriptionError(resourceProvider.getString(R.string.addtransaction_description_validation_error))
            }
            else -> {
                view.handleInvalidDescriptionError("")
            }
        }
    }

    override fun saveTransaction() {
        if (transaction.isNotValid) {
            validateForm()
            return
        }

        launch {
            transactionsRepository.save(transactionModelMapper.map(transaction))
                .onSuccess {
                    view.showMessage(resourceProvider.getString(R.string.addtransaction_save_success))
                    view.backToHome()
                }
                .onFailure { view.showMessage(resourceProvider.getString(R.string.addtransaction_save_failure)) }
        }
    }

    private fun validateForm() {
        validateValue()
        validateCategory()
        validateDate()
        validateDescription()
    }

}