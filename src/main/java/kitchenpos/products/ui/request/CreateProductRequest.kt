package kitchenpos.products.ui.request

import java.math.BigDecimal

data class CreateProductRequest(
    val name: String,
    val price: BigDecimal,
)
