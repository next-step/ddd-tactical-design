package kitchenpos.tobe.product.application.dto.response

import java.math.BigDecimal
import java.util.*

data class CreateProductResponse(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
)
