package kitchenpos.menus.tode.domain

import kitchenpos.menus.domain.Menu
import kitchenpos.menus.domain.MenuGroup
import kitchenpos.menus.domain.MenuProduct
import java.math.BigDecimal
import java.util.*

object MenuCreateFactory {
    fun createMenu(
        name: String,
        price: BigDecimal,
        menuGroup: MenuGroup,
        isDisplayed: Boolean,
        menuProducts: List<MenuProduct>,
    ) = Menu().apply {
        this.id = UUID.randomUUID()
        this.name = name
        this.price = price
        this.menuGroup = menuGroup
        this.isDisplayed = isDisplayed
        this.menuProducts = menuProducts
    }
}
