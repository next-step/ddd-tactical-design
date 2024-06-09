package kitchenpos.products.tobe.event

import kitchenpos.common.Price
import java.util.*

data class ProductPriceChanged(
    val productId: UUID,
    val price: Price,
)
