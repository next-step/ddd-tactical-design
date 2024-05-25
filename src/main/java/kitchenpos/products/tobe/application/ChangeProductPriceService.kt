package kitchenpos.products.tobe.application

import kitchenpos.products.domain.Product
import kitchenpos.products.tobe.domain.ProductPriceValidator
import kitchenpos.products.tobe.domain.UpdateProductPriceService
import kitchenpos.products.tobe.port.ProductReader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Transactional
@Service
class ChangeProductPriceService(
    private val productReader: ProductReader,
) {
    fun changeProductPrice(
        productId: UUID,
        price: BigDecimal?,
    ): Product {
        ProductPriceValidator.requireNormalPrice(price)

        return productReader.findProductById(productId).apply {
            UpdateProductPriceService.updatePrice(this, price!!)
        }
    }
}
