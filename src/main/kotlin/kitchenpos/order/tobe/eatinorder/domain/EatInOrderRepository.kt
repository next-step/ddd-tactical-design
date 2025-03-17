package kitchenpos.order.tobe.eatinorder.domain

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository("tobeEatInOrderRepository")
@Primary
interface EatInOrderRepository {
    fun existsByOrderTableAndStatusNot(orderTable: OrderTable?, status: EatInOrderStatus?): Boolean
}

