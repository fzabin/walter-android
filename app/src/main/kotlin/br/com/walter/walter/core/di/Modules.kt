package br.com.walter.walter.core.di

import androidx.room.Room
import br.com.walter.walter.core.persistence.AppDatabase
import br.com.walter.walter.core.persistence.AppDatabaseCallback
import br.com.walter.walter.core.persistence.DATABASE_NAME
import br.com.walter.walter.core.persistence.getDatabaseMigrations
import br.com.walter.walter.core.platform.base.AppResourceProvider
import br.com.walter.walter.core.provider.ResourceProvider
import br.com.walter.walter.features.categories.data.CategoriesDataSource
import br.com.walter.walter.features.categories.data.CategoryDtoMapper
import br.com.walter.walter.features.categories.domain.CategoriesRepository
import br.com.walter.walter.features.transactions.data.TransactionDtoMapper
import br.com.walter.walter.features.transactions.data.TransactionsDataSource
import br.com.walter.walter.features.transactions.domain.TransactionsRepository
import br.com.walter.walter.features.transactions.presentation.AddTransactionContract
import br.com.walter.walter.features.transactions.presentation.AddTransactionPresenter
import br.com.walter.walter.features.transactions.presentation.TransactionModelMapper
import br.com.walter.walter.features.transactiontypes.data.TransactionTypeDtoMapper
import br.com.walter.walter.features.transactiontypes.data.TransactionTypesDataSource
import br.com.walter.walter.features.transactiontypes.domain.TransactionTypesRepository
import org.koin.dsl.module

val persistenceModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, DATABASE_NAME)
        .addMigrations(*getDatabaseMigrations())
        .addCallback(AppDatabaseCallback)
        .build() }
    single { get<AppDatabase>().transactionTypeDao() }
    single { get<AppDatabase>().categoryDao() }
    single { get<AppDatabase>().transactionDao() }
}

val mapperModule = module {
    factory { TransactionTypeDtoMapper() }
    factory { CategoryDtoMapper() }
    factory { TransactionDtoMapper() }
    factory { TransactionModelMapper() }
}

val repositoryModule = module {
    single<TransactionTypesRepository> { TransactionTypesDataSource(get(), get()) }
    single<CategoriesRepository> { CategoriesDataSource(get(), get()) }
    single<TransactionsRepository> { TransactionsDataSource(get(), get()) }
}

val presentationModule = module {
    factory<AddTransactionContract.Presenter> { (view: AddTransactionContract.View) -> AddTransactionPresenter(view, get(), get()) }
}

val resourceModule = module {
    single<ResourceProvider> { AppResourceProvider(get()) }
}