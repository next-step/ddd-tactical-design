package kitchenpos.testsupport

import java.util.Optional
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kitchenpos.eatinorders.domain.Order
import kitchenpos.eatinorders.domain.OrderRepository
import kitchenpos.eatinorders.domain.OrderStatus
import kitchenpos.eatinorders.domain.OrderTable

class FakeOrderRepository : OrderRepository {
    private val storage: MutableMap<UUID, Order> = ConcurrentHashMap()

    override fun save(order: Order): Order {
        storage[order.id] = order
        return order
    }

    override fun findById(orderId: UUID): Optional<Order> {
        return Optional.ofNullable(storage[orderId])
    }

    override fun existsByOrderTableAndStatusNot(
        orderTable: OrderTable,
        orderStatus: OrderStatus
    ): Boolean {
        return storage.values
            .any {
                it.orderTable.id == orderTable.id &&
                        it.status == orderStatus
            }
    }

    override fun findAll(): MutableList<Order> {
        return storage.values.toMutableList()
    }
}