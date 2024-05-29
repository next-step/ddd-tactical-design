package kitchenpos.products.tobe.ui.dto

import java.math.BigDecimal

data class CreateProductRequest(
    val name: String,
    val price: BigDecimal,
)
