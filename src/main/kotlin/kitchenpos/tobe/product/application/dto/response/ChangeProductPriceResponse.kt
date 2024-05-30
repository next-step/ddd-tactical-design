package kitchenpos.tobe.product.application.dto.response

import java.math.BigDecimal
import java.util.UUID

data class ChangeProductPriceResponse(
    val productId: UUID,
    val price: BigDecimal,
)
