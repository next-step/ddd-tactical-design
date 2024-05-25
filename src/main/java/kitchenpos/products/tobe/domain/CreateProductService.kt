package kitchenpos.products.tobe.domain

import kitchenpos.products.domain.Product
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class CreateProductService(
    private val productNameValidator: ProductNameValidator,
) {
    fun createProduct(
        price: BigDecimal?,
        name: String?,
    ): Product {
        ProductPriceValidator.requireNormalPrice(price)
        productNameValidator.requireNormalName(name)

        return Product().apply {
            this.id = UUID.randomUUID()
            this.name = name
            this.price = price
        }
    }
}
