package kitchenpos.tobe.menu.application.dto.response

import kitchenpos.tobe.menu.domain.entity.MenuV2
import java.math.BigDecimal
import java.util.UUID

data class ChangeMenuPriceResponse(
    val menuId: UUID,
    val price: BigDecimal,
) {
    companion object {
        fun of(menu: MenuV2): ChangeMenuPriceResponse {
            return ChangeMenuPriceResponse(
                menuId = menu.id,
                price = menu.getPrice(),
            )
        }
    }
}
