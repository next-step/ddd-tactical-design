package kitchenpos.order.tobe.eatinorder.infra

import java.util.*
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableRepository


class FakeOrderTableRepository(
    private val orderTables: MutableMap<UUID, OrderTable> = mutableMapOf()
) : OrderTableRepository {
    override fun save(orderTable: OrderTable): OrderTable {
        orderTables[orderTable.id] = orderTable
        return orderTable
    }

    override fun findById(id: UUID): Optional<OrderTable> {
        return Optional.ofNullable(orderTables[id])
    }

    override fun findAll(): List<OrderTable> {
        return orderTables.values.toList()
    }
}
