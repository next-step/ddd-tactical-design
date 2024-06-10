package kitchenpos.products.tobe.application.dto

import java.util.UUID

data class ProductResponse(
    val id: UUID,
    val name: String,
    val price: Int,
)
