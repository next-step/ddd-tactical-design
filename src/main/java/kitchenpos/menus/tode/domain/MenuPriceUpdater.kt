package kitchenpos.menus.tode.domain

import kitchenpos.menus.domain.Menu
import java.math.BigDecimal

object MenuPriceUpdater {
    fun updateMenuPrice(
        price: BigDecimal,
        menu: Menu,
    ) {
        menu.price = price
    }
}