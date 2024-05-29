package kitchenpos.tobe.product.domain.repository

import kitchenpos.tobe.product.domain.entity.ProductV2
import java.util.*

interface ProductRepository {
    fun save(productV2: ProductV2): ProductV2

    fun findById(id: UUID): Optional<ProductV2>

    fun findAll(): List<ProductV2>

    fun findAllByIdIn(ids: List<UUID>): List<ProductV2>
}
