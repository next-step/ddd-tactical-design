package kitchenpos.products.domain

import java.util.*

data class Product(
    private val id: UUID,
    private val name: ProductName,
    private val price: ProductPrice,
)
