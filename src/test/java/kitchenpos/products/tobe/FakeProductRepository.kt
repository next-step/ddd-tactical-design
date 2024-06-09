package kitchenpos.products.tobe

import kitchenpos.products.tobe.domain.Product
import kitchenpos.products.tobe.domain.ProductRepository
import java.util.*

class FakeProductRepository : ProductRepository {
    private val productMap: MutableMap<UUID, Product> = mutableMapOf()

    override fun save(product: Product): Product {
        productMap[product.id] = product

        return product
    }

    override fun findById(id: UUID): Product? {
        return productMap[id]
    }

    override fun findAll(): List<Product> {
        return productMap.values.toList()
    }

    override fun findAllByIdIn(ids: List<UUID>): List<Product> {
        return productMap.filterValues { ids.contains(it.id) }.values.toList()
    }
}
