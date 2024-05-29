package kitchenpos.products.domain

import kitchenpos.common.Price
import java.util.*

data class ProductPriceChanged(
    val productId: UUID,
    val price: Price,
)
