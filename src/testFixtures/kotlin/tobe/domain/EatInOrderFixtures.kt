package tobe.domain

import kitchenpos.tobe.eatinorder.domain.entity.EatInOrder
import tobe.domain.OrderLineItemV2Fixtures.createOrderLineItemV2
import tobe.domain.OrderTableV2Fixtures.createOrderTableV2
import java.time.LocalDateTime

object EatInOrderFixtures {
    fun createEatInOrder(): EatInOrder {
        return EatInOrder.of(
            orderDateTime = LocalDateTime.now(),
            orderLineItems =
                listOf(createOrderLineItemV2()),
            orderTable = createOrderTableV2(),
        )
    }
}
