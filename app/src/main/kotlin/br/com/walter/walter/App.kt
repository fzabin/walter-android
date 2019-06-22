package br.com.walter.walter

import android.app.Application
import br.com.walter.walter.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    persistenceModule,
                    mapperModule,
                    repositoryModule,
                    presentationModule,
                    resourceModule
                )
            )
        }
    }
}