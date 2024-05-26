package kitchenpos.products.tobe.application

import kitchenpos.products.domain.Product
import kitchenpos.products.domain.ProductRepository
import kitchenpos.products.tobe.domain.ProductPriceValidator
import kitchenpos.products.tobe.domain.UpdateProductPriceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Transactional
@Service
class ChangeProductPriceService(
    private val productRepository: ProductRepository,
) {
    fun changeProductPrice(
        productId: UUID,
        price: BigDecimal?,
    ): Product {
        ProductPriceValidator.requireNormalPrice(price)

        return productRepository.findById(productId).orElseThrow { NoSuchElementException() }
            .apply {
                UpdateProductPriceService.updatePrice(this, price!!)
            }
    }
}
