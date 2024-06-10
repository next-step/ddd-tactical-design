package kitchenpos.menus.tobe.domain.menu

import java.util.UUID

interface TobeProductClient {
    fun validateAllProductsExists(menuProductIds: List<UUID>): Boolean
}
