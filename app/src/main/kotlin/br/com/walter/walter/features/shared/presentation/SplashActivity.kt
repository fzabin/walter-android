package br.com.walter.walter.features.shared.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.walter.walter.R
import br.com.walter.walter.features.home.presentation.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        val background = object: Thread() {
            override fun run() {
                try {
                    navigateToHome()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        background.start()
    }

    fun navigateToHome() {
        val intent = Intent(baseContext, HomeActivity::class.java)
        startActivity(intent)
    }

}