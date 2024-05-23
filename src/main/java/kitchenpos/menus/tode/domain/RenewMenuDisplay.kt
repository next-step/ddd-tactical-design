package kitchenpos.menus.tode.domain

import kitchenpos.menus.domain.Menu
import org.springframework.stereotype.Component

@Component
class RenewMenuDisplay(
    private val menuPriceValidator: MenuPriceValidator,
) {
    fun renewMenusDisplay(
        menu: Menu,
    ) {
        if (menuPriceValidator.isMenuDisplayable(menu)) {
            menu.isDisplayed = false
        }
    }
}