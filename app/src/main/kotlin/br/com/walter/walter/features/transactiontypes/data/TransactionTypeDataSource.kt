package br.com.walter.walter.features.transactiontypes.data

import br.com.walter.walter.core.functional.*
import br.com.walter.walter.features.transactiontypes.domain.TransactionType
import br.com.walter.walter.features.transactiontypes.domain.TransactionTypeRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TransactionTypeDataSource(
    private val transactionTypeDao: TransactionTypeDao,
    private val transactionTypeDtoMapper: TransactionTypeDtoMapper
): TransactionTypeRepository {

    override suspend fun getAll(): Result<List<TransactionType>> = withContext(IO) {
        resultFrom {
            transactionTypeDao.getAll()
        }.mapCatching { transactionTypeDtoMapper.mapList(it) }
    }

}