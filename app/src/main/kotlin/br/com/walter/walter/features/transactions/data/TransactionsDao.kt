package br.com.walter.walter.features.transactions.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface TransactionsDao {

    @Insert(onConflict = REPLACE)
    fun save(TransactionDto: TransactionDto): Long

    @Query("SELECT t.id, t.uuid, t.value, t.date, t.description, t.category_id, c.transaction_type_id FROM `transaction` t INNER JOIN category c ON t.category_id = c.id WHERE date BETWEEN :startDate AND :endDate")
    fun getTransactionsBetweenDates(startDate: String, endDate: String): List<TransactionWithTypeDto>

}