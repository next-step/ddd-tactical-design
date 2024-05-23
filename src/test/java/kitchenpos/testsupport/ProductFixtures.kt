package kitchenpos.testsupport

import java.math.BigDecimal
import java.util.UUID
import kitchenpos.products.domain.Product

object ProductFixtures {
    fun createProduct(
        id: UUID? = UUID.randomUUID(),
        name: String? = "test-product-name",
        price: BigDecimal = 1000.toBigDecimal()
    ): Product {
        return Product().apply {
            this.id = id
            this.name = name
            this.price = price
        }
    }
}