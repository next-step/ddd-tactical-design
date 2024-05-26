package kitchenpos.products.tobe.ui.dto

import java.math.BigDecimal

data class ProductCreateRequest(
    val name: String,
    val price: BigDecimal,
)
