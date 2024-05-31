package kitchenpos.tobe.menu.application.dto.request

import java.math.BigDecimal
import java.util.*

data class CreateMenuRequest(
    val name: String,
    val price: BigDecimal,
    val menuGroupId: UUID,
    val displayed: Boolean,
    val menuProducts: List<MenuProduct>,
) {
    data class MenuProduct(
        val productId: UUID,
        val quantity: Long,
    )
}
