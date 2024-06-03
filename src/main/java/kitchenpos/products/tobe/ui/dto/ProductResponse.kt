package kitchenpos.products.tobe.ui.dto

import kitchenpos.products.tobe.domain.Product
import java.math.BigDecimal
import java.util.UUID

data class ProductResponse(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
) {
    companion object {
        fun from(product: Product): ProductResponse {
            return ProductResponse(
                id = product.id,
                name = product.name.value,
                price = product.price.value,
            )
        }
    }
}
