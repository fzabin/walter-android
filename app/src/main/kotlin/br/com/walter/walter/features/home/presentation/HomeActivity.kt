package br.com.walter.walter.features.home.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import br.com.walter.walter.features.transactions.presentation.AddTransactionActivity
import br.com.walter.walter.R
import kotlinx.android.synthetic.main.home_activity.*

const val ADD_REQUEST = 1

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        setupActionBar()
        setupStatusBarColor()

        home_balance_section.setOnClickListener { }
        home_expenses_section.setOnClickListener { }
        home_investments_section.setOnClickListener { }
        home_add_button.setOnClickListener { navigateToAddTransaction(ADD_REQUEST) }
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

}
