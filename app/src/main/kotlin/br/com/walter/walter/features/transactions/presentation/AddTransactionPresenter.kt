package br.com.walter.walter.features.transactions.presentation

import br.com.walter.walter.R
import br.com.walter.walter.core.platform.CoroutinePresenter
import br.com.walter.walter.core.functional.onFailure
import br.com.walter.walter.core.functional.onSuccess
import br.com.walter.walter.core.provider.ResourceProvider
import br.com.walter.walter.core.util.DateFormatter
import br.com.walter.walter.features.categories.domain.Category
import br.com.walter.walter.features.categories.domain.CategoriesRepository
import kotlinx.coroutines.launch

const val EXPENSE_TYPE_ID = 1L
const val INCOME_TYPE_ID = 2L
const val INVESTMENT_TYPE_ID = 3L

class AddTransactionPresenter(
    private val view: AddTransactionContract.View,
    private val categoriesRepository: CategoriesRepository,
    private val resourceProvider: ResourceProvider
) : AddTransactionContract.Presenter, CoroutinePresenter() {

    private var categories: List<Category>? = null
    private var expenseCategories: List<Category>? = null
    private var incomeCategories: List<Category>? = null
    private var investmentCategories: List<Category>? = null

    private var date = ""
    private var value = 0.0
    private var description = ""
    private var selectedCategory = Category(0L, "", 0L)

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
        selectedCategory = Category(0L, "", 0L)
    }

    override fun onDateSelected(date: String) {
        setDate(date)
        validateDate()
        view.setDateField(date)
    }

    private fun setDate(date: String) {
        this.date = date
    }

    override fun setDefaultDate() {
        view.setDateField(DateFormatter().nowAsBrFormat())
    }

    override fun showCategoryDialog() {
        view.showCategoryDialog(categories!!)
    }

    override fun onCategorySelected(category: Category) {
        selectedCategory = category
        view.setCategoryField(category.description)
        validateCategory()
    }

    override fun updateValue(value: Double) {
        this.value = value
    }

    override fun updateDescription(description: String) {
        this.description = description
    }

    override fun handleValueField(value: String) {
        setValue(value)
        validateValue()
    }

    private fun setValue(newValue: String) {
        value = if (newValue.isEmpty()) 0.0 else newValue.toDouble()
    }

    private fun validateValue() {
        when {
            value <= 0.0 -> {
                view.handleInvalidValueError(resourceProvider.getString(R.string.addtransaction_value_validation_error))
            }
            else -> {
                view.handleInvalidValueError("")
            }
        }
    }

    private fun validateCategory() {
        when {
            selectedCategory.description.isEmpty() -> {
                view.handleInvalidCategoryError(resourceProvider.getString(R.string.addtransaction_category_validation_error))
            }
            else -> {
                view.handleInvalidCategoryError("")
            }
        }
    }

    private fun validateDate() {
        when {
            date.isEmpty() -> {
                view.handleInvalidCategoryError(resourceProvider.getString(R.string.addtransaction_date_validation_error))
            }
            else -> {
                view.handleInvalidCategoryError("")
            }
        }
    }
}