package kitchenpos.tobe.menu.application.dto.response

import kitchenpos.tobe.menu.domain.entity.MenuV2
import java.math.BigDecimal
import java.util.*

data class CreateMenuResponse(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val menuGroup: MenuGroup,
    val menuProducts: List<MenuProduct>,
    val displayed: Boolean,
) {
    data class MenuGroup(
        val id: UUID,
        val name: String,
    )

    data class MenuProduct(
        val seq: Long,
        val quantity: Long,
        val price: BigDecimal,
    )

    companion object {
        fun of(saved: MenuV2): CreateMenuResponse {
            return CreateMenuResponse(
                id = saved.id,
                name = saved.getName(),
                price = saved.getPrice(),
                menuGroup =
                    MenuGroup(
                        id = saved.menuGroup.id,
                        name = saved.menuGroup.name,
                    ),
                menuProducts =
                    saved.getMenuProducts().map {
                        MenuProduct(
                            seq = it.seq!!,
                            quantity = it.quantity,
                            price = it.price,
                        )
                    },
                displayed = saved.displayed,
            )
        }
    }
}
