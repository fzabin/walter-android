package br.com.walter.walter.features.transactions.data

import androidx.room.ColumnInfo
import br.com.walter.walter.core.functional.TwoWayMapper
import br.com.walter.walter.features.categories.data.CATEGORY_TRANSACTION_TYPE_ID_COLUMN_NAME
import br.com.walter.walter.features.transactions.domain.TransactionWithType
import java.math.BigDecimal

data class TransactionWithTypeDto(
    val id: Long,
    val uuid: String,
    val value: String,
    val date: String,
    val description: String,
    @ColumnInfo(name = TRANSACTION_CATEGORY_ID_COLUMN_NAME)
    val categoryId: Long,
    @ColumnInfo(name = CATEGORY_TRANSACTION_TYPE_ID_COLUMN_NAME)
    val transactionTypeId: Long
)

class TransactionWithTypeDtoMapper : TwoWayMapper<TransactionWithTypeDto, TransactionWithType> {

    override fun map(param: TransactionWithTypeDto): TransactionWithType = with(param) {
        TransactionWithType(
            id = id,
            uuid = uuid,
            value = BigDecimal(value),
            date = date,
            description = description,
            categoryId = categoryId,
            transactionTypeId = transactionTypeId
        )
    }

    override fun mapReverse(param: TransactionWithType): TransactionWithTypeDto = with(param) {
        TransactionWithTypeDto(
            id = id,
            uuid = uuid,
            value = value.toString(),
            date = date,
            description = description,
            categoryId = categoryId,
            transactionTypeId = transactionTypeId
        )
    }
}