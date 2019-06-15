package br.com.walter.walter.features.addtransaction.presentation

import br.com.walter.walter.core.platform.base.BasePresenter
import br.com.walter.walter.core.platform.base.BaseView
import br.com.walter.walter.features.addtransaction.Category

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

    }

}