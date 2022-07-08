package br.com.walter.walter.features.categories.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CategoriesDao {

    @Query("Select * from $CATEGORY_TABLE_NAME;")
    fun getAll(): List<CategoryDto>

}