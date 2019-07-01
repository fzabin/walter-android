package br.com.walter.walter.features.categories.data

import br.com.walter.walter.core.functional.*
import br.com.walter.walter.features.categories.domain.TransactionType
import br.com.walter.walter.features.categories.domain.TransactionTypesRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TransactionTypesDataSource(
    private val transactionTypesDao: TransactionTypesDao,
    private val transactionTypeDtoMapper: TransactionTypeDtoMapper
): TransactionTypesRepository {

    override suspend fun getAll(): Result<List<TransactionType>> = withContext(IO) {
        resultFrom {
            transactionTypesDao.getAll()
        }.mapCatching { transactionTypeDtoMapper.mapList(it) }
    }

}