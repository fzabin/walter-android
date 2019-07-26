package br.com.walter.walter

import android.app.Application
import br.com.walter.walter.core.di.categoriesModule
import br.com.walter.walter.core.di.persistenceModule
import br.com.walter.walter.core.di.resourceModule
import br.com.walter.walter.core.di.transactionsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    resourceModule,
                    persistenceModule,
                    categoriesModule,
                    transactionsModule
                )
            )
        }
    }
}