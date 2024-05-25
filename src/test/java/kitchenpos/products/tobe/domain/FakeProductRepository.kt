package kitchenpos.products.tobe.domain

import jakarta.transaction.NotSupportedException
import kitchenpos.products.domain.Product
import kitchenpos.products.domain.ProductRepository
import java.util.*

class FakeProductRepository : ProductRepository {
    private val repository: MutableMap<UUID, Product> = mutableMapOf()

    override fun save(product: Product): Product {
        val product = product.apply {
            id = UUID.randomUUID()
        }
        repository[product.id] = product
        return product
    }

    override fun findById(id: UUID): Optional<Product> {
        return Optional.of(repository[id] ?: run {
            throw NoSuchElementException()
        })
    }

    override fun findAll(): MutableList<Product> {
        throw NotSupportedException()
    }

    override fun findAllByIdIn(ids: MutableList<UUID>): MutableList<Product> {
        throw NotSupportedException()
    }

    fun deleteAll() {
        repository.clear()
    }
}