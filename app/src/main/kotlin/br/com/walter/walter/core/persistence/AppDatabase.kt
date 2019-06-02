package br.com.walter.walter.core.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.walter.walter.features.addtransaction.data.TransactionTypeDao
import br.com.walter.walter.features.addtransaction.data.TransactionTypeDto

const val DATABASE_NAME = "br.com.walter.walter.db"

const val DATABASE_VERSION_1 = 1

@Database(
    entities = [
        TransactionTypeDto::class
    ],
    version = DATABASE_VERSION_1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionTypeDao(): TransactionTypeDao

}