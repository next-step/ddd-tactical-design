package kitchenpos.products.tobe.application

import kitchenpos.products.domain.Product
import kitchenpos.products.tobe.domain.ProductPriceValidator
import kitchenpos.products.tobe.port.ProductReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Transactional
@Service
class ChangeProductPriceService(
    private val productPriceValidator: ProductPriceValidator,
    private val productReader: ProductReader,
) {
    fun changeProductPrice(
        productId: UUID,
        price: BigDecimal?,
    ): Product {
        productPriceValidator.requireNormalPrice(price)

        return productReader.findProductById(productId).apply {
            this.price = price
        }
    }
}
