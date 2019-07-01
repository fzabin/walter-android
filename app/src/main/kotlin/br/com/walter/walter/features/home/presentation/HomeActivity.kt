package br.com.walter.walter.features.home.presentation

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.walter.walter.R
import br.com.walter.walter.core.archcomponents.extension.observe
import br.com.walter.walter.core.util.NumberFormatter
import br.com.walter.walter.features.transactions.presentation.AddTransactionActivity
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

const val ADD_REQUEST = 1

class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val numberFormatter: NumberFormatter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupActionBar()
        setupStatusBarColor()

        homeViewModel.run {
            observe(totalExpenses, ::setTotalExpenses)
            observe(totalInvestments, ::setTotalInvestments)
            observe(balance, ::setBalance)
        }

        homeViewModel.getMonthlySummary()

        home_balance_section.setOnClickListener { }
        home_expenses_section.setOnClickListener { }
        home_investments_section.setOnClickListener { }
        home_add_button.setOnClickListener { navigateToAddTransaction(ADD_REQUEST) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            homeViewModel.getMonthlySummary()
        }
    }

    private fun setBalance(balance: BigDecimal) {
        home_balance_text.text = numberFormatter.getCurrencyFormat(balance)
    }

    private fun setTotalExpenses(totalExpenses: BigDecimal) {
        home_expenses_text.text = numberFormatter.getCurrencyFormat(totalExpenses)
    }

    private fun setTotalInvestments(totalInvestments: BigDecimal) {
        home_investments_text.text = numberFormatter.getCurrencyFormat(totalInvestments)
    }

    private fun setupActionBar() {
        supportActionBar?.hide()
    }

    private fun setupStatusBarColor() {
        val color = ContextCompat.getColor(this, R.color.colorPrimary)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    private fun navigateToAddTransaction(request: Int) {
        val intent = Intent(this, AddTransactionActivity::class.java)
            .putExtra("request_code", request)

        startActivityForResult(intent, request)
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
