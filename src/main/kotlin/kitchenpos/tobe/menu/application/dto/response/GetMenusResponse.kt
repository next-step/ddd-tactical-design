package kitchenpos.tobe.menu.application.dto.response

import kitchenpos.tobe.menu.domain.entity.MenuV2
import java.math.BigDecimal
import java.util.*

data class GetMenusResponse(
    val menus: List<Menu>,
) {
    data class Menu(
        val id: UUID,
        val name: String,
        val price: BigDecimal,
        val menuGroup: MenuGroup,
        val displayed: Boolean,
        val menuProducts: List<MenuProduct>,
    )

    data class MenuGroup(
        val id: UUID,
        val name: String,
    )

    data class MenuProduct(
        val seq: Long,
        val price: BigDecimal,
        val quantity: Long,
        val productId: UUID,
    )

    companion object {
        fun of(menus: List<MenuV2>): GetMenusResponse {
            return GetMenusResponse(
                menus =
                    menus.map {
                        Menu(
                            id = it.id,
                            name = it.getName(),
                            price = it.getPrice(),
                            menuProducts =
                                it.getMenuProducts().map { menuProduct ->
                                    MenuProduct(
                                        seq = menuProduct.seq!!,
                                        productId = menuProduct.productId,
                                        price = menuProduct.price,
                                        quantity = menuProduct.quantity,
                                    )
                                },
                            displayed = it.displayed,
                            menuGroup =
                                MenuGroup(
                                    id = it.menuGroup.id,
                                    name = it.menuGroup.name,
                                ),
                        )
                    },
            )
        }
    }
}
