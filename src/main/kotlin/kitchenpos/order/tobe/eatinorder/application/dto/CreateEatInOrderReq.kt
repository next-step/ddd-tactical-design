package kitchenpos.order.tobe.eatinorder.application.dto

import java.util.*

data class CreateEatInOrderReq(
    val orderLineItems: List<CreateEatInOrderLineItemReq>,
    val orderTableId: UUID,
)
