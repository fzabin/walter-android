package br.com.walter.walter.features.transactions.presentation

import br.com.walter.walter.core.platform.base.BasePresenter
import br.com.walter.walter.core.platform.base.BaseView
import br.com.walter.walter.features.categories.domain.Category

interface AddTransactionContract {

    interface View: BaseView<Presenter> {

        fun showMessage(message: String)

        fun setDateField(text: String)

        fun setCategoryField(text: String)

        fun showCategoryDialog(categories: List<Category>)

    }

    interface Presenter: BasePresenter {

        fun getAllCategories()

        fun onDateSelected(date: String)

        fun setDefaultDate()

        fun showCategoryDialog()

        fun onCategorySelected(category: Category)

        fun updateDate(date: String)

        fun updateValue(value: Double)

        fun updateDescription(description: String)

    }

}