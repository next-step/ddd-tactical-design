package kitchenpos.order.tobe.eatinorder.domain

import java.util.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository("tobeOrderTableRepository")
@Primary
interface OrderTableRepository {
    fun save(orderTable: OrderTable): OrderTable

    fun findById(id: UUID): Optional<OrderTable>

    fun findAll(): List<OrderTable>
}

