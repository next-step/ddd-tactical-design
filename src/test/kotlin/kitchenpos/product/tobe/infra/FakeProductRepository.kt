package kitchenpos.product.tobe.infra

import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductRepository
import java.util.*

class FakeProductRepository(
    private val products: MutableMap<UUID, Product> = mutableMapOf()

) : ProductRepository {

    override fun save(product: Product): Product {
        if (product.id == null) {
            product.id = UUID.randomUUID()
        }
        products[product.id!!] = product
        return product
    }

    override fun findById(id: UUID): Optional<Product> {
        return Optional.ofNullable(products[id])
    }

    override fun findAll(): List<Product> {
        return products.values.toList()
    }

    override fun findAllByIdIn(ids: List<UUID>): List<Product> {
        return ids.map { products[it]!! }
    }
}
