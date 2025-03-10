package kitchenpos.menu.tobe.infra

import java.math.BigDecimal
import java.util.*
import kitchenpos.menu.tobe.domain.ProductClient
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.stereotype.Component

@Component
class DefaultProductClient(
    private val productRepository: ProductRepository,
) : ProductClient {
    override fun getProductPrice(productId: UUID): BigDecimal {
        return productRepository.findById(productId)
            .orElseThrow { throw NoSuchElementException("상품을 찾을 수 없습니다.") }
            .productPrice.price
    }
}
