package br.com.walter.walter.core.di

import androidx.room.Room
import br.com.walter.walter.core.persistence.AppDatabase
import br.com.walter.walter.core.persistence.AppDatabaseCallback
import br.com.walter.walter.core.persistence.DATABASE_NAME
import br.com.walter.walter.core.persistence.getDatabaseMigrations
import br.com.walter.walter.core.platform.base.AppResourceProvider
import br.com.walter.walter.core.provider.ResourceProvider
import br.com.walter.walter.core.util.DateFormatter
import br.com.walter.walter.core.util.NumberFormatter
import br.com.walter.walter.features.categories.data.CategoriesDataSource
import br.com.walter.walter.features.categories.data.CategoryDtoMapper
import br.com.walter.walter.features.categories.domain.CategoriesRepository
import br.com.walter.walter.features.home.presentation.HomeViewModel
import br.com.walter.walter.features.transactions.data.TransactionDtoMapper
import br.com.walter.walter.features.transactions.data.TransactionWithTypeDtoMapper
import br.com.walter.walter.features.transactions.data.TransactionsDataSource
import br.com.walter.walter.features.transactions.domain.TransactionsRepository
import br.com.walter.walter.features.transactions.presentation.AddTransactionContract
import br.com.walter.walter.features.transactions.presentation.AddTransactionPresenter
import br.com.walter.walter.features.transactions.presentation.TransactionModelMapper
import br.com.walter.walter.features.categories.data.TransactionTypeDtoMapper
import br.com.walter.walter.features.categories.data.TransactionTypesDataSource
import br.com.walter.walter.features.categories.domain.TransactionTypesRepository
import br.com.walter.walter.features.categories.domain.usecase.GetCategories
import br.com.walter.walter.features.transactions.domain.usecase.AddTransaction
import br.com.walter.walter.features.transactions.domain.usecase.GetCurrentMonthSummary
import br.com.walter.walter.features.transactions.domain.usecase.GetCurrentMonthTransactionsByType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val resourceModule = module {
    single<ResourceProvider> { AppResourceProvider(get()) }
    factory { DateFormatter() }
    factory { NumberFormatter() }
}

val persistenceModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, DATABASE_NAME)
            .addMigrations(*getDatabaseMigrations())
            .addCallback(AppDatabaseCallback)
            .build()
    }

    single { get<AppDatabase>().transactionTypeDao() }
    single { get<AppDatabase>().categoryDao() }
    single { get<AppDatabase>().transactionDao() }
}

val categoriesModule = module {
    factory { TransactionTypeDtoMapper() }
    factory { CategoryDtoMapper() }

    single<TransactionTypesRepository> { TransactionTypesDataSource(get(), get()) }
    single<CategoriesRepository> { CategoriesDataSource(get(), get()) }

    factory { GetCategories(get()) }
}

val transactionsModule = module {
    factory { TransactionDtoMapper() }
    factory { TransactionModelMapper() }
    factory { TransactionWithTypeDtoMapper() }

    single<TransactionsRepository> { TransactionsDataSource(get(), get(), get()) }

    factory { GetCurrentMonthSummary(get(), get()) }
    factory { AddTransaction(get()) }
    factory { GetCurrentMonthTransactionsByType(get(), get()) }

    factory<AddTransactionContract.Presenter> { (view: AddTransactionContract.View) ->
        AddTransactionPresenter(
            view,
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel { HomeViewModel(get()) }
}