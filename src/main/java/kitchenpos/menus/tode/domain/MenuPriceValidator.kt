package kitchenpos.menus.tode.domain

import kitchenpos.menus.domain.Menu
import org.springframework.stereotype.Component

@Component
class MenuPriceValidator {
    fun isMenuDisplayable(
        menu: Menu,
    ): Boolean {
        val sum = menu.menuProducts
            .sumOf { it.product.price * it.quantity.toBigDecimal() }

        return menu.price > sum
    }
}
