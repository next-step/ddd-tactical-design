package kitchenpos.eatinorders.tobe.domain

import java.util.*

interface OrderTableRepositoryV2 {
    fun save(orderTable: OrderTable): OrderTable
    fun findByIdOrNull(orderTableId: UUID): OrderTable?
    fun findAll(): List<OrderTable>
}
