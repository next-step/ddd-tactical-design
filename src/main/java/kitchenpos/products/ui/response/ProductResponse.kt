package kitchenpos.products.ui.response

import kitchenpos.products.domain.Product
import java.math.BigDecimal

data class ProductResponse(
    val id: String,
    val name: String,
    val price: BigDecimal,
) {
    companion object {
        @JvmStatic
        fun of(product: Product): ProductResponse {
            return ProductResponse(
                id = product.id.toString(),
                name = product.getName(),
                price = product.getPrice()
            )
        }
    }
}
