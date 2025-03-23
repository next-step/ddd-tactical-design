package kitchenpos.order.tobe.eatinorder.domain

interface OrderTableEmptyService {
    fun canEmpty(orderTable: OrderTable): Boolean
}
