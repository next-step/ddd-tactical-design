package kitchenpos.menus.tode.domain

import kitchenpos.menus.domain.Menu

object RenewMenuDisplay {
    fun renewMenusDisplay(
        menu: Menu,
    ) {
        if (MenuPriceValidator.isMenuDisplayable(menu)) {
            menu.isDisplayed = false
        }
    }
}