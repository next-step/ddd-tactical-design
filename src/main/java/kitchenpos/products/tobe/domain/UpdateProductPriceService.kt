package kitchenpos.products.tobe.domain

import kitchenpos.products.domain.Product
import java.math.BigDecimal

object UpdateProductPriceService {
    fun updatePrice(
        product: Product,
        renewPrice: BigDecimal,
    ) = product.apply {
        this.price = renewPrice
    }
}
