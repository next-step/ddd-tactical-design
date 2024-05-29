package kitchenpos.menus.application.tobe

import kitchenpos.common.Price
import kitchenpos.common.price
import kitchenpos.menus.tobe.domain.Menu
import kitchenpos.menus.tobe.domain.MenuPriceValidatorService
import java.math.BigDecimal
import java.util.*

val FAKE_MENU_PRODUCTS_TOTAL_PRICES = BigDecimal.valueOf(10000).price()

object FakeMenuPriceValidatorService : MenuPriceValidatorService {
    override fun validate(menu: Menu) {
        if (menu.price <= FAKE_MENU_PRODUCTS_TOTAL_PRICES) {
            throw IllegalArgumentException("메뉴상품 가격의 총합보다 메뉴의 가격이 클 수 없습니다")
        }
    }

    override fun validate(menu: Menu, productPriceById: Map<UUID, Price>) {
        if (menu.price <= FAKE_MENU_PRODUCTS_TOTAL_PRICES) {
            validate(menu)
        }
    }
}
