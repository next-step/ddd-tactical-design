package kitchenpos.order.tobe.common

import java.util.*
import org.springframework.stereotype.Repository

@Repository("tobeOrderRepository")
interface OrderRepository {
    fun save(order: Order): Order

    fun findById(id: UUID): Optional<Order>

    fun findAll(): List<Order>
}

