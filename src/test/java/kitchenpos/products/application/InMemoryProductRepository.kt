package kitchenpos.products.application

import kitchenpos.products.domain.Product
import kitchenpos.products.domain.ProductRepository
import java.util.*

class InMemoryProductRepository : ProductRepository {
    private val products: MutableMap<UUID, Product> = mutableMapOf()

    override fun save(product: Product): Product {
        products[product.id] = product
        return product
    }

    override fun findById(id: UUID?): Optional<Product> {
        return Optional.ofNullable(products[id])
    }

    override fun findAll(): List<Product> {
        return products.values.toList()
    }

    override fun findAllByIdIn(ids: MutableList<UUID>?): MutableList<Product> {
        return products.filterKeys { ids?.contains(it) ?: false }.values.toMutableList()
    }
}
