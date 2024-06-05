package tobe.domain

import kitchenpos.tobe.eatinorder.domain.entity.OrderLineItemV2
import java.util.*

object OrderLineItemV2Fixtures {
    fun createOrderLineItemV2(): OrderLineItemV2 {
        return OrderLineItemV2(
            seq = 1,
            menuId = UUID.randomUUID(),
            quantity = 1,
            price = 16000.toBigDecimal(),
        )
    }
}
