package br.com.walter.walter.features.categories.data

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.mapCatching
import br.com.walter.walter.core.functional.resultFrom
import br.com.walter.walter.features.categories.domain.Category
import br.com.walter.walter.features.categories.domain.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriesDataSource(
    private val categoriesDao: CategoriesDao,
    private val categoryDtoMapper: CategoryDtoMapper
) : CategoriesRepository {

    override suspend fun getAll(): Result<List<Category>> = withContext(Dispatchers.IO) {
        resultFrom {
            categoriesDao.getAll()
        }.mapCatching { categoryDtoMapper.mapList(it) }
    }

}