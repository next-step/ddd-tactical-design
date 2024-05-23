package kitchenpos.products.tobe.port

import kitchenpos.products.domain.Product
import java.util.*

interface ProductReader {
    fun findProductById(
        productId: UUID,
    ): Product
}
