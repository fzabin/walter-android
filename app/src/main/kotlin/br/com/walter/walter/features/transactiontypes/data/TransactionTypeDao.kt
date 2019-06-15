package br.com.walter.walter.features.transactiontypes.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TransactionTypeDao {

    @Query("Select * from $TRANSACTION_TYPE_TABLE_NAME")
    fun getAll(): List<TransactionTypeDto>

}