package kitchenpos.menu.tobe.infra

import java.util.*
import kitchenpos.menu.tobe.domain.ProductClient
import kitchenpos.menu.tobe.domain.ProductInfo
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.stereotype.Service

@Service
class DefaultProductClient(
    private val productRepository: ProductRepository,
) : ProductClient {
    override fun getProducts(productIds: List<UUID>): Map<UUID, ProductInfo> {
        return productRepository.findAllByIdIn(productIds)
            .associate { it.id to ProductInfo(it.id, it.productPrice.price) }
    }
}
