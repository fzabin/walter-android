package br.com.walter.walter.features.addtransaction.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TransactionDao {

    @Insert(onConflict = REPLACE)
    fun save(TransactionDto: TransactionDto): Long

}