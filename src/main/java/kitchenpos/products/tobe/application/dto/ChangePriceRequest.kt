package kitchenpos.products.tobe.application.dto

import java.util.UUID

data class ChangePriceRequest(
    val productId: UUID,
    val price: Int,
)
