package kitchenpos.menus.tode.port

import kitchenpos.menus.domain.Menu
import java.util.UUID

interface MenuReader {
    fun findAllByProductId(
        productId: UUID
    ): List<Menu>
}
