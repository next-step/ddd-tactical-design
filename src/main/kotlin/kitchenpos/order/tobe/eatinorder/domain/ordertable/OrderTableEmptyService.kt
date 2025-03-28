package kitchenpos.order.tobe.eatinorder.domain.ordertable

interface OrderTableEmptyService {
    fun canEmpty(orderTable: OrderTable): Boolean
}
