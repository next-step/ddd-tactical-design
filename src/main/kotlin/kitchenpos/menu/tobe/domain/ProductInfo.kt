package kitchenpos.menu.tobe.domain

import java.math.BigDecimal
import java.util.*

data class ProductInfo(
    val productId: UUID,
    val price: BigDecimal,
) {
}
