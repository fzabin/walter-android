package br.com.walter.walter.features.categories.domain.usecase

import br.com.walter.walter.core.functional.Result
import br.com.walter.walter.core.functional.UseCase
import br.com.walter.walter.features.categories.domain.CategoriesRepository
import br.com.walter.walter.features.categories.domain.Category

class GetCategories(
    private val categoriesRepository: CategoriesRepository
) : UseCase<List<Category>>() {

    override suspend fun run(): Result<List<Category>> {
        return categoriesRepository.getAll()
    }
}