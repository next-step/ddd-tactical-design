package kitchenpos.eatinorders.tobe.dto.`in`

import kitchenpos.common.Price
import java.util.*

data class EatInOrderCreateRequest(
    val orderTableId: UUID,
    val eatInOrderLineItemCreateRequests: List<EatInOrderLineItemCreateRequest>
)

data class EatInOrderLineItemCreateRequest(
    val menuId: UUID,
    val price: Price,
    val quantity: Long
)
