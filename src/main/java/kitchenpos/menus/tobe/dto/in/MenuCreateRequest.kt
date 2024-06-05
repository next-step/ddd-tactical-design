package kitchenpos.menus.tobe.dto.`in`

import kitchenpos.common.Price
import java.util.UUID

data class MenuCreateRequest(
    val name: String,
    val price: Price,
    val menuGroupId: UUID,
    val displayed: Boolean,
    val menuProducts: List<MenuProductCreateRequest>
) {
    val productIds: List<UUID>
        get() = menuProducts.map { it.productId }
}

data class MenuProductCreateRequest(
    val productId: UUID,
    val quantity: Long
)
