package br.com.walter.walter.features.transactions.presentation

import br.com.walter.walter.core.functional.TwoWayMapper
import br.com.walter.walter.features.transactions.domain.Transaction
import java.math.BigDecimal

data class TransactionModel(
    val id: Long,
    val uuid: String,
    val value: BigDecimal,
    val date: String,
    val description: String,
    val categoryId: Long
) {

    companion object {
        fun empty() = TransactionModel(0, "", BigDecimal.ZERO, "", "", 0)
    }

    val valueIsNotValid
        get() = value.compareTo(BigDecimal.ZERO) == 0

    val categoryIsNotValid
        get() = categoryId == 0L

    private val dateIsNotValid
        get() = date.length != 10

    val isNotValid
        get() = valueIsNotValid
                || dateIsNotValid
                || description.isEmpty()
                || categoryIsNotValid

}

class TransactionModelMapper : TwoWayMapper<TransactionModel, Transaction> {

    override fun map(param: TransactionModel): Transaction = with(param) {
        Transaction(
            id = id,
            uuid = uuid,
            value = value,
            date = date,
            description = description,
            categoryId = categoryId
        )
    }

    override fun mapReverse(param: Transaction): TransactionModel = with(param) {
        TransactionModel(
            id = id,
            uuid = uuid,
            value = value,
            date = date,
            description = description,
            categoryId = categoryId
        )
    }
}
