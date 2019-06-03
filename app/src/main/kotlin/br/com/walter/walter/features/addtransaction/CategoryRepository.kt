package br.com.walter.walter.features.addtransaction

import br.com.walter.walter.core.functional.Result

interface CategoryRepository {

    suspend fun getAll(): Result<List<Category>>

}