package kitchenpos.tobe.eatinorder.application.dto.request

import java.util.*

data class CreateOrderTableRequest(
    val id: UUID,
    val numberOfGuests: Int,
    val name: String,
    val occupied: Boolean,
)
