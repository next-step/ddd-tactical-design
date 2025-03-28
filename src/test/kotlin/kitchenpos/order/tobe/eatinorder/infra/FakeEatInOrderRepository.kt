package kitchenpos.order.tobe.eatinorder.infra

import java.util.*
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus

class FakeEatInOrderRepository(
    private val eatInOrders: MutableMap<UUID, EatInOrder> = mutableMapOf()
) : EatInOrderRepository {
    override fun save(order: EatInOrder): EatInOrder {
        eatInOrders[order.id] = order
        return order
    }

    override fun findById(id: UUID): Optional<EatInOrder> {
        return Optional.ofNullable(eatInOrders[id])
    }

    override fun existsByOrderTableIdAndStatusNot(orderTableId: UUID, orderStatus: EatInOrderStatus): Boolean {
        return eatInOrders.values.any { it.orderTableId == orderTableId && it.status != orderStatus }
    }
}
