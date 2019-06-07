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
import br.com.walter.walter.features.addtransaction.data.CategoryDataSource
import br.com.walter.walter.features.addtransaction.data.CategoryDtoMapper
import br.com.walter.walter.features.home.presentation.ADD_EXPENSE_REQUEST
import br.com.walter.walter.features.home.presentation.ADD_INCOME_REQUEST
import br.com.walter.walter.features.home.presentation.ADD_INVESTMENT_REQUEST
import kotlinx.android.synthetic.main.addtransaction_activity.*

const val WITHOUT_ELEVATION = 0F
const val REQUEST_CODE = "request_code"

class AddTransactionActivity : AppCompatActivity(), AddTransactionContract.View {

    override lateinit var presenter: AddTransactionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addtransaction_activity)

        setupStatusBarColor()

        intent?.getIntExtra(REQUEST_CODE, 0)?.let {
            setupLayout(it)
        }

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

    }

    private fun setupStatusBarColor() {
        val color = ContextCompat.getColor(this, R.color.colorPrimary)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    private fun setupLayout(requestCode: Int) {
        when (requestCode) {
            ADD_INCOME_REQUEST -> {
                setupActionBar(getString(R.string.addproduction_income_title))
                setupButton(getString(R.string.addproduction_income_button), this.getColor(R.color.colorIncome))
            }
            ADD_EXPENSE_REQUEST -> {
                setupActionBar(getString(R.string.addproduction_expense_title))
                setupButton(getString(R.string.addproduction_expense_button), this.getColor(R.color.colorExpense))
            }
            ADD_INVESTMENT_REQUEST -> {
                setupActionBar(getString(R.string.addproduction_investment_title))
                setupButton(getString(R.string.addproduction_investment_button), this.getColor(R.color.colorInvestment))
            }
        }
    }

    private fun setupActionBar(title: String) {
        supportActionBar?.elevation = WITHOUT_ELEVATION
        supportActionBar?.title = title
    }

    private fun setupButton(text: String, color: Int) {
        addproduction_add_button.text = text
        addproduction_add_button.backgroundTintList = ColorStateList.valueOf(color)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
