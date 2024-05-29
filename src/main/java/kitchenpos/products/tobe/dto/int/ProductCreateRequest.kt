package kitchenpos.products.tobe.dto.int

import kitchenpos.common.Price

data class ProductCreateRequest(
    val displayedName: String,
    val price: Price
)
