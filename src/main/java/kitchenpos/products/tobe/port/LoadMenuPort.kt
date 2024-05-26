package kitchenpos.products.tobe.port

import kitchenpos.menus.domain.Menu
import java.util.UUID

interface LoadMenuPort {
    fun findAllByProductId(
        productId: UUID
    ): List<Menu>
}
