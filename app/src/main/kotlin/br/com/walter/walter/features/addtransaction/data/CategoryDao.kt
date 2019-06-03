package br.com.walter.walter.features.addtransaction.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CategoryDao {

    @Query("Select * from $CATEGORY_TABLE_NAME;")
    fun getAll(): List<CategoryDto>

}