package kitchenpos.order.tobe.eatinorder.domain

import java.util.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository("tobeEatInOrderRepository")
@Primary
interface EatInOrderRepository {
    fun save(order: EatInOrder): EatInOrder
    fun findById(id: UUID): Optional<EatInOrder>
    fun existsByOrderTableAndStatusNot(orderTable: OrderTable?, status: EatInOrderStatus?): Boolean
}

