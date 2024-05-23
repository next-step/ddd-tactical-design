package kitchenpos.products.tobe.adapter

import kitchenpos.products.domain.Product
import kitchenpos.products.tobe.port.ProductReader
import java.util.*

class FakeProductReader: ProductReader {
    var exceptionTrigger = false
    override fun findProductById(productId: UUID): Product {
        if (exceptionTrigger) {
            throw NoSuchElementException()
        }
        return Product().apply {
            this.id = productId
        }
    }
}
