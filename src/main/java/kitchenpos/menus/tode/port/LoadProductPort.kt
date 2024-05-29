package kitchenpos.menus.tode.port

import kitchenpos.products.domain.Product
import java.util.UUID

interface LoadProductPort {
    fun findAllByIdIn(
        ids: List<UUID>
    ): List<Product>

    fun findById(
        id: UUID
    ): Product?
}
