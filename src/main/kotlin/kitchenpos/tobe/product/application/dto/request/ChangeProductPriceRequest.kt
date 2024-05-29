package kitchenpos.tobe.product.application.dto.request

import java.math.BigDecimal

data class ChangeProductPriceRequest(
    val price: BigDecimal,
)
