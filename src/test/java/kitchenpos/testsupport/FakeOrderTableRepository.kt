package kitchenpos.testsupport

import java.util.Optional
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kitchenpos.eatinorders.domain.OrderTable
import kitchenpos.eatinorders.domain.OrderTableRepository

class FakeOrderTableRepository : OrderTableRepository {
    private val storage: MutableMap<UUID, OrderTable> = ConcurrentHashMap()

    override fun findById(orderTableId: UUID): Optional<OrderTable> {
        return Optional.ofNullable(storage[orderTableId])
    }

    override fun save(orderTable: OrderTable): OrderTable {
        storage[orderTable.id] = orderTable
        return orderTable
    }

    override fun findAll(): MutableList<OrderTable> {
        return storage.values.toMutableList()
    }
}