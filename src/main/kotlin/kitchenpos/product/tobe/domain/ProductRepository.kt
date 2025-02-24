package kitchenpos.product.tobe.domain

import java.util.*

interface ProductRepository {
    fun save(product: Product): Product

    fun findById(id: UUID): Optional<Product>

    fun findAll(): List<Product>

    fun findAllByIdIn(ids: List<UUID>): List<Product>
}
