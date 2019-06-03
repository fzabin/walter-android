package br.com.walter.walter.features.addtransaction.data

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.mapCatching
import br.com.walter.walter.core.functional.resultFrom
import br.com.walter.walter.features.addtransaction.Category
import br.com.walter.walter.features.addtransaction.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryDataSource(
    private val categoryDao: CategoryDao,
    private val categoryDtoMapper: CategoryDtoMapper
) : CategoryRepository {

    override suspend fun getAll(): Result<List<Category>> = withContext(Dispatchers.IO) {
        resultFrom {
            categoryDao.getAll()
        }.mapCatching { categoryDtoMapper.mapList(it) }
    }

}