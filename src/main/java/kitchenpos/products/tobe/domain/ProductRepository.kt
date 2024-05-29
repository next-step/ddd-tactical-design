package kitchenpos.products.tobe.domain

import java.util.*

interface ProductRepository {
    fun save(product: Product): Product

    fun findById(id: UUID): Product?

    fun findAll(): List<Product>

    fun findAllByIdIn(ids: List<UUID>): List<Product>

    fun flush()
}
