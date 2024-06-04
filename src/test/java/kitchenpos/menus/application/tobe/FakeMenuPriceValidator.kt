package kitchenpos.menus.application.tobe

import kitchenpos.common.price
import kitchenpos.menus.tobe.domain.Menu
import kitchenpos.menus.tobe.domain.MenuPriceValidator
import java.math.BigDecimal

val FAKE_MENU_PRODUCTS_TOTAL_PRICES = BigDecimal.valueOf(50000).price()

object FakeMenuPriceValidator : MenuPriceValidator {
    override fun validate(menu: Menu) {
        if (menu.price > FAKE_MENU_PRODUCTS_TOTAL_PRICES) {
            throw IllegalArgumentException("메뉴상품 가격의 총합보다 메뉴의 가격이 클 수 없습니다")
        }
    }

    override fun isValid(menu: Menu): Boolean {
        return menu.price <= FAKE_MENU_PRODUCTS_TOTAL_PRICES
    }
}
