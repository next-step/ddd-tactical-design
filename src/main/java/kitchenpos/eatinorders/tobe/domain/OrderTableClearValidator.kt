package kitchenpos.eatinorders.tobe.domain

interface OrderTableClearValidator {
    fun validate(orderTable: OrderTable)

    fun isValid(orderTable: OrderTable): Boolean
}
