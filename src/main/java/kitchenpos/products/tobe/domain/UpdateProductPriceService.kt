package kitchenpos.products.tobe.domain

import kitchenpos.products.domain.Product
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class UpdateProductPriceService {
    fun updatePrice(
        product: Product,
        renewPrice: BigDecimal,
    ) = product.apply {
        this.price = renewPrice
    }
}
