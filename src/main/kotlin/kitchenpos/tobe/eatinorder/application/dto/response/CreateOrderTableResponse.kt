package kitchenpos.tobe.eatinorder.application.dto.response

import java.util.*

data class CreateOrderTableResponse(
    val id: UUID,
    val numberOfGuests: Int,
    val name: String,
    val occupied: Boolean,
) {
    companion object {
        fun of(
            id: UUID,
            numberOfGuests: Int,
            name: String,
            occupied: Boolean,
        ): CreateOrderTableResponse {
            return CreateOrderTableResponse(
                id = id,
                numberOfGuests = numberOfGuests,
                name = name,
                occupied = occupied,
            )
        }
    }
}
