package kitchenpos.product.tobe.domain

import java.util.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository("tobeProductRepository")
@Primary
interface ProductRepository {
    fun save(product: Product): Product

    fun findById(id: UUID): Optional<Product>

    fun findAll(): List<Product>

    fun findAllByIdIn(ids: List<UUID>): List<Product>
}
