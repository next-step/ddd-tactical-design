package kitchenpos.products.tobe.dto.out

import kitchenpos.common.Price
import java.util.*

data class ProductResponse(
    val id: UUID,
    val name: String,
    val price: Price
)
