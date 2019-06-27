package br.com.walter.walter.features.transactions.presentation

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import br.com.walter.walter.R
import br.com.walter.walter.core.extension.addTextChangedListener
import br.com.walter.walter.core.extension.clearError
import br.com.walter.walter.core.extension.showError
import br.com.walter.walter.features.categories.domain.Category
import br.com.walter.walter.features.categories.presentation.CategoryDialog
import br.com.walter.walter.features.shared.presentation.DatePicker
import kotlinx.android.synthetic.main.addtransaction_activity.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

const val WITHOUT_ELEVATION = 0F

class AddTransactionActivity : AppCompatActivity(), AddTransactionContract.View {

    override val presenter: AddTransactionContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addtransaction_activity)

        setupActionBar()
        setupStatusBarColor()
        setupInitialButtonColor()

        presenter.start()

        addtransaction_expense_option.setOnClickListener {
            setupLayout(getString(R.string.addtransaction_expense_button), this.getColor(R.color.colorExpense))
        }
        addtransaction_income_option.setOnClickListener {
            setupLayout(getString(R.string.addtransaction_income_button), this.getColor(R.color.colorIncome))
        }
        addtransaction_investment_option.setOnClickListener {
            setupLayout(getString(R.string.addtransaction_investment_button), this.getColor(R.color.colorInvestment))
        }

        val datePicker = DatePicker(this) { date, _ ->
            presenter.onDateSelected(date)
        }

        addtransaction_date_field.setOnClickListener { datePicker.show() }
        addtransaction_category_field.setOnClickListener { presenter.showCategoryDialog() }
        addtransaction_transaction_type_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.addtransaction_expense_option -> presenter.filterCategories(EXPENSE_TYPE_ID)
                R.id.addtransaction_income_option -> presenter.filterCategories(INCOME_TYPE_ID)
                R.id.addtransaction_investment_option -> presenter.filterCategories(INVESTMENT_TYPE_ID)
            }
        }
        addtransaction_value_field.addTextChangedListener(afterTextChanged = presenter::handleValueField)
        addtransaction_description_field.addTextChangedListener(afterTextChanged = presenter::handleDescriptionField)
        addtransaction_add_button.setOnClickListener { presenter.saveTransaction() }
    }

    private fun setupActionBar() {
        supportActionBar?.elevation = WITHOUT_ELEVATION
        supportActionBar?.title = getString(R.string.addtransaction_default_title)
    }

    private fun setupStatusBarColor() {
        val color = ContextCompat.getColor(this, R.color.colorPrimary)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    private fun setupInitialButtonColor() {
        addtransaction_add_button.backgroundTintList = ColorStateList.valueOf(this.getColor(R.color.colorExpense))
    }

    private fun setupLayout(buttonText: String, color: Int) {
        setupButton(buttonText, color)
        setupSegmentedGroup(color)
    }

    private fun setupSegmentedGroup(color: Int) {
        addtransaction_transaction_type_group.setTintColor(color)
    }

    private fun setupButton(text: String, color: Int) {
        addtransaction_add_button.text = text
        addtransaction_add_button.backgroundTintList = ColorStateList.valueOf(color)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun setDateField(text: String) {
        addtransaction_date_field.setText(text)
    }

    override fun setCategoryField(text: String) {
        addtransaction_category_field.setText(text)
    }

    override fun showCategoryDialog(categories: List<Category>) {
        CategoryDialog(window.decorView as ViewGroup, this)
            .list(
                categories = categories,
                selected = { presenter.onCategorySelected(it) }
            )
    }

    override fun handleInvalidValueError(message: String) {
        if (message.isNotEmpty()) {
            addtransaction_value_layout.showError(message)
        } else {
            addtransaction_value_layout.clearError()
        }
    }

    override fun handleInvalidCategoryError(message: String) {
        if (message.isNotEmpty()) {
            addtransaction_category_layout.showError(message)
        } else {
            addtransaction_category_layout.clearError()
        }
    }

    override fun handleInvalidDateError(message: String) {
        if (message.isNotEmpty()) {
            addtransaction_date_layout.showError(message)
        } else {
            addtransaction_date_layout.clearError()
        }
    }

    override fun handleInvalidDescriptionError(message: String) {
        if (message.isNotEmpty()) {
            addtransaction_description_layout.showError(message)
        } else {
            addtransaction_description_layout.clearError()
        }
    }

    override fun backToHome() {
        this.finish()
    }
}
