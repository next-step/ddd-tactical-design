package kitchenpos.eatinorders.tobe.dto

import java.util.*

data class CreateOrderRequest(
    val orderLineItems: List<CreateOrderLineItemRequest>,
    val orderTableId: UUID,
)

data class CreateOrderLineItemRequest(
    val menuInfo: CreateOrderLineItemMenuRequest,
    val quantity: Int,
)

data class CreateOrderLineItemMenuRequest(
    val menuId: UUID,
    val price: Int,
)
