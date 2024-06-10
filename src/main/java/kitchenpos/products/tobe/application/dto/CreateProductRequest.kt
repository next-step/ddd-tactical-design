package kitchenpos.products.tobe.application.dto

import java.util.UUID

data class CreateProductRequest(
    val id: UUID,
    val name: String,
    val price: Int,
)
