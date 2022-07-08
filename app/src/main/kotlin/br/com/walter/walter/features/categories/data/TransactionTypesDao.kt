package br.com.walter.walter.features.categories.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TransactionTypesDao {

    @Query("Select * from $TRANSACTION_TYPE_TABLE_NAME")
    fun getAll(): List<TransactionTypeDto>

}