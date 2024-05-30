package kitchenpos.tobe.product.domain.event

import java.math.BigDecimal
import java.util.*

data class ProductPriceChangedEvent(
    val productId: UUID,
    val price: BigDecimal,
)
