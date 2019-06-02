package br.com.walter.walter.features.addtransaction.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import br.com.walter.walter.core.functional.TwoWayMapper
import br.com.walter.walter.features.addtransaction.TransactionType

const val TRANSACTION_TYPE_TABLE_NAME = "transaction_type"
const val TRANSACTION_TYPE_INITIAL_SETUP = "INSERT INTO `transaction_type` VALUES (1, 'Expense'), (2, 'Income'), (3, 'Investment')"

@Entity(tableName = TRANSACTION_TYPE_TABLE_NAME )
data class TransactionTypeDto(
    @PrimaryKey
    val id: Long,
    val description: String
) {
    @Ignore
    constructor() : this(0, "")
}

class TransactionTypeDtoMapper : TwoWayMapper<TransactionTypeDto, TransactionType> {

    override fun map(param: TransactionTypeDto): TransactionType = with(param) {
        TransactionType(
            id = id,
            description = description
        )
    }

    override fun mapReverse(param: TransactionType): TransactionTypeDto = with(param) {
        TransactionTypeDto(
            id = id,
            description = description
        )
    }
}