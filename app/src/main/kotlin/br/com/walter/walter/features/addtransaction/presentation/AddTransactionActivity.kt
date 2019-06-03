package br.com.walter.walter.features.addtransaction.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import br.com.walter.walter.R
import br.com.walter.walter.core.persistence.AppDatabase
import br.com.walter.walter.core.persistence.AppDatabaseCallback
import br.com.walter.walter.core.persistence.DATABASE_NAME
import br.com.walter.walter.core.persistence.getDatabaseMigrations
import br.com.walter.walter.features.addtransaction.data.CategoryDataSource
import br.com.walter.walter.features.addtransaction.data.CategoryDtoMapper
import br.com.walter.walter.features.addtransaction.data.TransactionTypeDataSource
import br.com.walter.walter.features.addtransaction.data.TransactionTypeDtoMapper

class AddTransactionActivity : AppCompatActivity(), AddTransactionContract.View {

    override lateinit var presenter: AddTransactionContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addtransaction_activity)

        val database = Room.databaseBuilder(this, AppDatabase::class.java, DATABASE_NAME)
            .addMigrations(*getDatabaseMigrations())
            .addCallback(AppDatabaseCallback)
            .build()

        val transactionTypeDao = database.transactionTypeDao()
        val transactionTypeDtoMapper = TransactionTypeDtoMapper()

        val categoryDao = database.categoryDao()
        val categoryDtoMapper = CategoryDtoMapper()

        val transactionTypeRepository = TransactionTypeDataSource(transactionTypeDao, transactionTypeDtoMapper)
        val categoryRepository = CategoryDataSource(categoryDao, categoryDtoMapper)

        presenter = AddTransactionPresenter(
            view = this,
            transactionTypeRepository = transactionTypeRepository,
            categoryRepository = categoryRepository)
        presenter.start()

    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
