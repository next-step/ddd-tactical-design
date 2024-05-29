package kitchenpos.tobe.eatinorder.domain.repository

import kitchenpos.order.common.domain.OrderStatus
import kitchenpos.tobe.eatinorder.domain.entity.EatInOrder
import kitchenpos.tobe.eatinorder.domain.entity.OrderTableV2
import java.util.*

interface EatInOrderRepository {
    fun save(order: EatInOrder): EatInOrder

    fun findEatInOrderById(orderId: UUID): EatInOrder

    fun findAll(): List<EatInOrder>

    fun existsByOrderTableAndStatusNot(
        orderTable: OrderTableV2,
        completed: OrderStatus,
    ): Boolean
}
