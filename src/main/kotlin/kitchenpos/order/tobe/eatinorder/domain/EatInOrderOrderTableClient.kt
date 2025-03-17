package kitchenpos.order.tobe.eatinorder.domain

import java.util.UUID

interface EatInOrderOrderTableClient {
    fun getOrderTable(orderTableId: UUID): EatInOrderOrderTableInfo
}
