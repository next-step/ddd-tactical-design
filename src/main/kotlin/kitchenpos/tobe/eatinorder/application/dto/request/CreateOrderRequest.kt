package kitchenpos.tobe.eatinorder.application.dto.request

import java.math.BigDecimal
import java.util.UUID

data class CreateOrderRequest(
    val orderLineItems: List<OrderLineItem>,
    val orderTable: OrderTable,
) {
    data class OrderLineItem(
        val menuId: UUID,
        val quantity: Int,
        val price: BigDecimal,
    )

    data class OrderTable(
        val id: UUID,
        val name: String,
        val numberOfGuests: Int,
        val occupied: Boolean,
    )
}
