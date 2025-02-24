package kitchenpos.product.tobe.application.dto

import java.math.BigDecimal

data class CreateProductReq(
    val name: String,
    val price: BigDecimal,
)
