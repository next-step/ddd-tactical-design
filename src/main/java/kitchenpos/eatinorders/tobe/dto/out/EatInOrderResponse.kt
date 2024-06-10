package kitchenpos.eatinorders.tobe.dto.out

import kitchenpos.common.Price
import kitchenpos.eatinorders.tobe.domain.EatInOrder
import kitchenpos.eatinorders.tobe.domain.EatInOrderStatus
import java.time.LocalDateTime
import java.util.UUID

data class EatInOrderResponse(
    val id: UUID,
    val status: EatInOrderStatus,
    val orderDateTime: LocalDateTime,
    val orderTableId: UUID,
    val orderLineItems: List<EatInOrderLineItemResponse>
)

data class EatInOrderLineItemResponse(
    val seq: Long,
    val menuId: UUID,
    val quantity: Long,
    val price: Price
)

fun fromEatInOrder(eatInOrder: EatInOrder): EatInOrderResponse {
    return EatInOrderResponse(
        id = eatInOrder.id,
        status = eatInOrder.status,
        orderDateTime = eatInOrder.orderDateTime,
        orderTableId = eatInOrder.orderTableId,
        orderLineItems = eatInOrder.eatInOrderLineItems.items
            .map {
                EatInOrderLineItemResponse(
                    seq = it.seq,
                    menuId = it.menuId,
                    quantity = it.quantity.value,
                    price = it.price
                )
            }
    )
}
