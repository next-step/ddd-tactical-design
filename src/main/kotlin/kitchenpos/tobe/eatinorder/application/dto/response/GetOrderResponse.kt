package kitchenpos.tobe.eatinorder.application.dto.response

import kitchenpos.tobe.eatinorder.domain.EatInOrderStatus
import kitchenpos.tobe.eatinorder.domain.entity.EatInOrder
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class GetOrderResponse(
    val id: UUID,
    val orderTableId: UUID,
    val orderStatus: EatInOrderStatus,
    val orderLineItems: List<OrderLineItemResponse>,
    val orderDateTime: LocalDateTime,
) {
    data class OrderLineItemResponse(
        val seq: Long,
        val menuId: UUID,
        val price: BigDecimal,
        val quantity: Int,
    )

    companion object {
        fun from(it: EatInOrder): GetOrderResponse {
            return GetOrderResponse(
                id = it.id,
                orderTableId = it.orderTable.id,
                orderStatus = it.status,
                orderLineItems =
                    it.getOrderLineItems().map {
                        OrderLineItemResponse(
                            seq = it.seq!!,
                            menuId = it.menuId,
                            price = it.price,
                            quantity = it.quantity,
                        )
                    },
                orderDateTime = it.orderDateTime,
            )
        }
    }
}
