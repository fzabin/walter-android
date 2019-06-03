package br.com.walter.walter.features.home.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.walter.walter.features.addtransaction.presentation.AddTransactionActivity
import br.com.walter.walter.R
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        home_add_fab.setOnClickListener { navigateToAddTransaction() }
    }

    private fun navigateToAddTransaction() {
        val intent = Intent(this, AddTransactionActivity::class.java)
        startActivity(intent)
    }

}
