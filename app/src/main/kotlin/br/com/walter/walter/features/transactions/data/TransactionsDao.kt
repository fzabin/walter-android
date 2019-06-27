package br.com.walter.walter.features.transactions.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface TransactionsDao {

    @Insert(onConflict = REPLACE)
    fun save(TransactionDto: TransactionDto): Long

    @Query("SELECT * FROM `transaction` WHERE date BETWEEN :startDate AND :endDate")
    fun getTransactionsBetweenDates(startDate: String, endDate: String): List<TransactionDto>

}