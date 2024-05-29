package kitchenpos.tobe.eatinorder.application.dto.response

import kitchenpos.tobe.eatinorder.domain.EatInOrderStatus
import kitchenpos.tobe.eatinorder.domain.entity.EatInOrder
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class CreateEatInOrderResponse(
    val id: UUID,
    val orderTableId: UUID,
    val orderLineItems: List<OrderLineItemResponse>,
    val orderStatus: EatInOrderStatus,
    val orderDateTime: LocalDateTime,
) {
    data class OrderLineItemResponse(
        val menuId: UUID,
        val price: BigDecimal,
        val quantity: Int,
    )

    companion object {
        fun of(eatInOrder: EatInOrder): CreateEatInOrderResponse {
            return CreateEatInOrderResponse(
                id = eatInOrder.id,
                orderTableId = eatInOrder.orderTable.id,
                orderLineItems =
                    eatInOrder.getOrderLineItems().map { orderLineItem ->
                        OrderLineItemResponse(
                            menuId = orderLineItem.menuId,
                            price = orderLineItem.price,
                            quantity = orderLineItem.quantity,
                        )
                    },
                orderStatus = eatInOrder.status,
                orderDateTime = eatInOrder.orderDateTime,
            )
        }
    }
}
