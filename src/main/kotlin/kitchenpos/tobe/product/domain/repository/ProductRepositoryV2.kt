package kitchenpos.tobe.product.domain.repository

import kitchenpos.tobe.product.domain.entity.ProductV2
import java.util.*

interface ProductRepositoryV2 {
    fun save(productV2: ProductV2): ProductV2

    fun findProductById(id: UUID): ProductV2?

    fun findAll(): List<ProductV2>

    fun findAllByIdIn(ids: List<UUID>): List<ProductV2>
}
