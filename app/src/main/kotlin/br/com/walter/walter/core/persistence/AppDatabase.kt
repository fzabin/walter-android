package br.com.walter.walter.core.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.walter.walter.features.transactions.data.*
import br.com.walter.walter.features.categories.data.CategoryDao
import br.com.walter.walter.features.categories.data.CategoryDto
import br.com.walter.walter.features.transactiontypes.data.TransactionTypeDao
import br.com.walter.walter.features.transactiontypes.data.TransactionTypeDto

const val DATABASE_NAME = "br.com.walter.walter.db"

const val DATABASE_VERSION_1 = 1
const val DATABASE_VERSION_2 = 2
const val DATABASE_VERSION_3 = 3

@Database(
    entities = [
        TransactionTypeDto::class,
        CategoryDto::class,
        TransactionDto::class
    ],
    version = DATABASE_VERSION_3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionTypeDao(): TransactionTypeDao

    abstract fun categoryDao(): CategoryDao

    abstract fun transactionDao(): TransactionsDao

}