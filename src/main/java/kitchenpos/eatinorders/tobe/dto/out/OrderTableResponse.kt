package kitchenpos.eatinorders.tobe.dto.out

import kitchenpos.eatinorders.tobe.domain.OrderTable
import java.util.UUID

data class OrderTableResponse(
    val id: UUID,
    val name: String,
    val occupied: Boolean,
    val numberOfGuests: Int
)

fun fromOrderTable(orderTable: OrderTable): OrderTableResponse {
    return OrderTableResponse(
        id = orderTable.id,
        name = orderTable.name.value,
        occupied = orderTable.occupied,
        numberOfGuests = orderTable.numberOfGuests.value
    )
}
