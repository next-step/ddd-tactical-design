package kitchenpos.menu.tobe.application.dto

import java.math.BigDecimal
import java.util.*
import kitchenpos.menu.tobe.domain.Menu
import kitchenpos.menu.tobe.domain.MenuDisplay

data class MenuResp(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val menuGroup: MenuGroupResp,
    val display: MenuDisplay,
    val menuProducts: List<MenuProductResp>
) {
    companion object {
        fun of(menu: Menu): MenuResp {
            return MenuResp(
                id = menu.id!!,
                name = menu.menuName.name,
                price = menu.menuPrice.price,
                menuGroup = MenuGroupResp.of(menu.menuGroup),
                display = menu.menuDisplay,
                menuProducts = menu.menuProducts.menuProducts.map { MenuProductResp.of(it) }
            )
        }
    }
}
