package br.com.walter.walter.features.addtransaction.presentation

import br.com.walter.walter.core.platform.base.BasePresenter
import br.com.walter.walter.core.platform.base.BaseView

interface AddTransactionContract {

    interface View: BaseView<Presenter> {

        fun showMessage(message: String)

        fun setDateField(text: String)

    }

    interface Presenter: BasePresenter {

        fun getAllCategories()

        fun onDateSelected(date: String)

        fun setDefaultDate()

    }

}