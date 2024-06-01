package kitchenpos.tobe.menu.domain.dto

import java.math.BigDecimal
import java.util.*

data class ProductPrice(
    val id: UUID,
    val price: BigDecimal,
)
