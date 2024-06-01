package kitchenpos.menus.tode.domain

import kitchenpos.menus.domain.Menu

object RenewMenuDisplay {
    fun renewMenusDisplay(
        menu: Menu,
    ) {
        if (MenuDisplayableChecker.isMenuDisplayable(menu)) {
            menu.isDisplayed = false
        }
    }

    fun updateDisplay(
        menu: Menu,
        displayed: Boolean,
    ) {
        menu.isDisplayed = displayed
    }
}