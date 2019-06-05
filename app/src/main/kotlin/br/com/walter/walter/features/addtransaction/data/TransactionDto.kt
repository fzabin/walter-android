package br.com.walter.walter.features.addtransaction.data

import androidx.room.*
import br.com.walter.walter.core.functional.TwoWayMapper
import br.com.walter.walter.features.addtransaction.Transaction
import java.math.BigDecimal

const val TRANSACTION_TABLE_NAME = "transaction"
const val TRANSACTION_CATEGORY_ID_COLUMN_NAME = "category_id"

@Entity(
    tableName = TRANSACTION_TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = CategoryDto::class,
        parentColumns = [CATEGORY_ID_COLUMN_NAME],
        childColumns = [TRANSACTION_CATEGORY_ID_COLUMN_NAME]
    )]
)
data class TransactionDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val uuid: String,
    val value: String,
    val date: String,
    val description: String,
    @ColumnInfo(name = TRANSACTION_CATEGORY_ID_COLUMN_NAME)
    val categoryId: Long
) {
    @Ignore
    constructor() : this(0, "", "", "", "", 0)
}

class TransactionDtoMapper : TwoWayMapper<TransactionDto, Transaction> {

    override fun map(param: TransactionDto): Transaction = with(param) {
        Transaction(
            id = id,
            uuid = uuid,
            value = BigDecimal(value),
            date = date,
            description = description,
            categoryId = categoryId
        )
    }

    override fun mapReverse(param: Transaction): TransactionDto = with(param) {
        TransactionDto(
            id = id,
            uuid = uuid,
            value = value.toString(),
            date = date,
            description = description,
            categoryId = categoryId
        )
    }
}