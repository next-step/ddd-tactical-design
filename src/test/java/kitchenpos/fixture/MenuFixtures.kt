package kitchenpos.fixture

import kitchenpos.common.Price
import kitchenpos.common.price
import kitchenpos.menus.application.tobe.FakeMenuNameValidator
import kitchenpos.menus.tobe.domain.*
import kitchenpos.menus.tobe.dto.`in`.MenuCreateRequest
import kitchenpos.menus.tobe.dto.`in`.MenuProductCreateRequest
import java.math.BigDecimal
import java.util.*

object MenuFixtures {
    fun menu(
        price: Price = BigDecimal.valueOf(15000).price(),
        menuProducts: MenuProducts = MenuProducts(listOf(menuProduct())),
        menuNameValidator: MenuNameValidator = FakeMenuNameValidator,
        displayStatus: Boolean = true
    ): Menu {
        return Menu.of(
            menuGroup = menuGroup(),
            name = MenuName.of("양념치킨", menuNameValidator),
            price = price,
            displayStatus = displayStatus,
            menuProducts = menuProducts,
        )
    }

    fun menuCreateRequest(
        menuGroupId: UUID,
        name: String = "양념치킨",
        price: Price = BigDecimal.valueOf(1000).price(),
        displayStatus: Boolean = true,
        menuProducts: List<MenuProductCreateRequest>
    ): MenuCreateRequest {
        return MenuCreateRequest(
            name = name,
            price = price,
            menuGroupId = menuGroupId,
            displayed = displayStatus,
            menuProducts = menuProducts
        )
    }

    fun menuGroup(): MenuGroup {
        return MenuGroup(MenuGroupName("치킨"))
    }

    fun menuProduct(
        productId: UUID = ProductFixtures.product().id,
        quantity: MenuProductQuantity = MenuProductQuantity(1),
        price: Price = BigDecimal.valueOf(20000).price()
    ): MenuProduct {
        return MenuProduct(productId = productId, quantity = quantity, price = price)
    }
}
