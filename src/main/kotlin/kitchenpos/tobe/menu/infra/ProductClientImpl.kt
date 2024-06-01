package kitchenpos.tobe.menu.infra

import kitchenpos.tobe.menu.domain.ProductClient
import kitchenpos.tobe.menu.domain.dto.ProductPrice
import kitchenpos.tobe.product.domain.repository.ProductRepositoryV2
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductClientImpl(
    private val productRepository: ProductRepositoryV2,
) : ProductClient {
    override fun getProductPrices(productIds: List<UUID>): List<ProductPrice> {
        val products = productRepository.findAllByIdIn(productIds)

        if (products.size != productIds.size) {
            throw IllegalArgumentException("상품이 존재하지 않습니다.")
        }

        return products.map { ProductPrice(it.id, it.getPrice()) }
    }
}
