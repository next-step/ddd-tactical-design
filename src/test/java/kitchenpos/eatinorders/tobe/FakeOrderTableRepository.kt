package kitchenpos.eatinorders.tobe

import kitchenpos.eatinorders.tobe.domain.OrderTable
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository
import java.util.*

object FakeOrderTableRepository : OrderTableRepository {
    private val orderTableMap: MutableMap<UUID, OrderTable> = mutableMapOf()

    override fun save(orderTable: OrderTable): OrderTable {
        orderTableMap[orderTable.id] = orderTable

        return orderTable
    }

    override fun findById(orderTableId: UUID): Optional<OrderTable> {
        return Optional.ofNullable(orderTableMap[orderTableId])
    }
}
