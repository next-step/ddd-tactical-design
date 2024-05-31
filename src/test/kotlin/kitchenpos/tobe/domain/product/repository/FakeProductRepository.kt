package kitchenpos.tobe.domain.product.repository

import kitchenpos.tobe.product.domain.entity.ProductV2
import kitchenpos.tobe.product.domain.repository.ProductRepositoryV2
import java.util.*

class FakeProductRepository : ProductRepositoryV2 {
    private val products: MutableMap<UUID, ProductV2> = mutableMapOf()

    override fun save(productV2: ProductV2): ProductV2 {
        products[productV2.id] = productV2
        return productV2
    }

    override fun findProductById(id: UUID): ProductV2? {
        return products[id]
    }

    override fun findAll(): List<ProductV2> {
        return products.values.toList()
    }

    override fun findAllByIdIn(ids: List<UUID>): List<ProductV2> {
        return products.filterKeys { ids.contains(it) }.values.toList()
    }
}
