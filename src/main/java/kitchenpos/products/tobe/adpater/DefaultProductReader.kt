package kitchenpos.products.tobe.adpater

import kitchenpos.products.domain.Product
import kitchenpos.products.domain.ProductRepository
import kitchenpos.products.tobe.port.ProductReader
import java.util.*

class DefaultProductReader(
    private val productRepository: ProductRepository,
) : ProductReader {
    override fun findProductById(productId: UUID): Product =
        productRepository.findById(productId).orElseThrow { NoSuchElementException() }
}
