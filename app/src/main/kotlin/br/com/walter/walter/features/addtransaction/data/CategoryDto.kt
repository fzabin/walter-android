package br.com.walter.walter.features.addtransaction.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import br.com.walter.walter.core.functional.TwoWayMapper
import br.com.walter.walter.features.addtransaction.Category

const val CATEGORY_TABLE_NAME = "category"
const val CATEGORY_ID_COLUMN_NAME = "id"
const val CATEGORY_TRANSACTION_TYPE_ID_COLUMN_NAME = "transaction_type_id"
const val CATEGORY_INITIAL_SETUP = "INSERT INTO `category` VALUES " +
        "(1, 'Bars and Restaurants', 1), " +
        "(2, 'Donation', 1), " +
        "(3, 'Education', 1), " +
        "(4, 'Health', 1), " +
        "(5, 'Home', 1), " +
        "(6, 'Loan', 1), " +
        "(7, 'Market', 1), " +
        "(8, 'Other', 1); " +
        "(9, 'Pets', 1), " +
        "(10, 'Service', 1), " +
        "(11, 'Transportation', 1), " +
        "(12, 'Vehicle', 1), " +
        "(13, 'Work', 1), " +
        "(14, 'Salary', 2), " +
        "(15, 'Loan', 2), " +
        "(16, 'Refund', 2), " +
        "(17, 'Other', 2), " +
        "(18, 'Savings', 3), " +
        "(19, 'Other', 3);"

@Entity(
    tableName = CATEGORY_TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = TransactionTypeDto::class,
        parentColumns = [TRANSACTION_TYPE_ID_COLUMN_NAME],
        childColumns = [CATEGORY_TRANSACTION_TYPE_ID_COLUMN_NAME],
        onDelete = CASCADE)
    ]
)
data class CategoryDto(
    @PrimaryKey
    val id: Long,
    val description: String,
    @ColumnInfo(name = "transaction_type_id")
    val transactionTypeId: Long
) {
    @Ignore
    constructor() : this(0, "", 0)
}

class CategoryDtoMapper : TwoWayMapper<CategoryDto, Category> {

    override fun map(param: CategoryDto): Category = with(param) {
        Category(
            id = id,
            description = description,
            transactionTypeId = transactionTypeId
        )
    }

    override fun mapReverse(param: Category): CategoryDto = with(param) {
        CategoryDto(
            id = id,
            description = description,
            transactionTypeId = transactionTypeId
        )
    }
}