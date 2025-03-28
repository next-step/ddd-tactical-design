package kitchenpos.order.tobe.eatinorder.application.dto

import java.util.*

data class CreateEatInOrderLineItemReq(
    val seq: Long,
    val menuId: UUID,
    val quantity: Long,
)
