package kitchenpos.tobe.menu.infra

import kitchenpos.tobe.menu.domain.ProductQueryClient
import kitchenpos.tobe.menu.domain.dto.ProductPrice
import kitchenpos.tobe.product.domain.repository.ProductRepositoryV2
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductClientImpl(
    private val productRepository: ProductRepositoryV2,
) : ProductQueryClient {
    override fun getProductPrices(productIds: List<UUID>): List<ProductPrice> {
        return productRepository.findAllByIdIn(productIds).map {
            ProductPrice(
                id = it.id,
                price = it.getPrice(),
            )
        }
    }
}
