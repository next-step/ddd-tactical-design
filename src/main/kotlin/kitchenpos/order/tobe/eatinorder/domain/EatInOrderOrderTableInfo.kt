package kitchenpos.order.tobe.eatinorder.domain

import java.util.*

data class EatInOrderOrderTableInfo(
    val orderTableId: UUID,
    val orderTableStatus: OrderTableStatus,
)
