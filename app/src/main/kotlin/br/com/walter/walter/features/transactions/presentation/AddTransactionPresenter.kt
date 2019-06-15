package br.com.walter.walter.features.transactions.presentation

import br.com.walter.walter.core.platform.CoroutinePresenter
import br.com.walter.walter.core.functional.onFailure
import br.com.walter.walter.core.functional.onSuccess
import br.com.walter.walter.core.util.DateFormatter
import br.com.walter.walter.features.categories.domain.Category
import br.com.walter.walter.features.categories.domain.CategoriesRepository
import kotlinx.coroutines.launch

const val EXPENSE_TYPE_ID = 1L
const val INCOME_TYPE_ID = 2L
const val INVESTMENT_TYPE_ID = 3L

class AddTransactionPresenter(
    private val view: AddTransactionContract.View,
    private val categoriesRepository: CategoriesRepository
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

    override fun onDateSelected(date: String) {
        view.setDateField(date)
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
    }

    override fun updateDate(date: String) {
        this.date = date
    }

    override fun updateValue(value: Double) {
        this.value = value
    }

    override fun updateDescription(description: String) {
        this.description = description
    }

}