package br.com.walter.walter.features.addtransaction.presentation

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.room.Room
import br.com.walter.walter.R
import br.com.walter.walter.core.persistence.AppDatabase
import br.com.walter.walter.core.persistence.AppDatabaseCallback
import br.com.walter.walter.core.persistence.DATABASE_NAME
import br.com.walter.walter.core.persistence.getDatabaseMigrations
import br.com.walter.walter.core.util.DateFormatter
import br.com.walter.walter.features.addtransaction.data.CategoryDataSource
import br.com.walter.walter.features.addtransaction.data.CategoryDtoMapper
import br.com.walter.walter.features.shared.presentation.DatePicker
import br.com.walter.walter.features.shared.presentation.datePicker
import kotlinx.android.synthetic.main.addtransaction_activity.*

const val WITHOUT_ELEVATION = 0F

class AddTransactionActivity : AppCompatActivity(), AddTransactionContract.View {

    override lateinit var presenter: AddTransactionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addtransaction_activity)

        setupActionBar()
        setupStatusBarColor()
        setupInitialButtonColor()

        val database = Room.databaseBuilder(this, AppDatabase::class.java, DATABASE_NAME)
            .addMigrations(*getDatabaseMigrations())
            .addCallback(AppDatabaseCallback)
            .build()

        val categoryDao = database.categoryDao()
        val categoryDtoMapper = CategoryDtoMapper()

        val categoryRepository = CategoryDataSource(categoryDao, categoryDtoMapper)

        presenter = AddTransactionPresenter(
            view = this,
            categoryRepository = categoryRepository
        )
        presenter.start()

        addproduction_expense_option.setOnClickListener {
            setupLayout(getString(R.string.addproduction_expense_button), this.getColor(R.color.colorExpense))
        }
        addproduction_income_option.setOnClickListener {
            setupLayout(getString(R.string.addproduction_income_button), this.getColor(R.color.colorIncome))
        }
        addproduction_investment_option.setOnClickListener {
            setupLayout(getString(R.string.addproduction_investment_button), this.getColor(R.color.colorInvestment))
        }

        val datePicker = DatePicker(this) { _, formattedDate ->
            addtransaction_date_field?.setText(formattedDate)
        }

        addtransaction_date_field.setOnClickListener { datePicker.show() }
        addtransaction_date_field.setText(DateFormatter().nowAsBrFormat())
    }

    private fun setupActionBar() {
        supportActionBar?.elevation = WITHOUT_ELEVATION
        supportActionBar?.title = getString(R.string.addproduction_default_title)
    }

    private fun setupStatusBarColor() {
        val color = ContextCompat.getColor(this, R.color.colorPrimary)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    private fun setupInitialButtonColor() {
        addproduction_add_button.backgroundTintList = ColorStateList.valueOf(this.getColor(R.color.colorExpense))
    }

    private fun setupLayout(buttonText: String, color: Int) {
        setupButton(buttonText, color)
        setupSegmentedGroup(color)
    }

    private fun setupSegmentedGroup(color: Int) {
        addproduction_transaction_type_group.setTintColor(color)
    }

    private fun setupButton(text: String, color: Int) {
        addproduction_add_button.text = text
        addproduction_add_button.backgroundTintList = ColorStateList.valueOf(color)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
