package kitchenpos.fixture

import kitchenpos.common.Price
import kitchenpos.common.price
import kitchenpos.menus.application.tobe.FakeMenuNameValidatorService
import kitchenpos.menus.application.tobe.FakeMenuPriceValidatorService
import kitchenpos.menus.tobe.domain.*
import kitchenpos.menus.tobe.dto.`in`.MenuCreateRequest
import kitchenpos.menus.tobe.dto.`in`.MenuProductCreateRequest
import java.math.BigDecimal
import java.util.UUID

object MenuFixtures {
    fun menu(
        price: Price = BigDecimal.valueOf(15000).price(),
        menuProducts: MenuProducts = MenuProducts(listOf(menuProduct())),
        menuNameValidatorService: MenuNameValidatorService = FakeMenuNameValidatorService,
        menuPriceValidatorService: MenuPriceValidatorService = FakeMenuPriceValidatorService,
        displayStatus: Boolean = true
    ): Menu {
        return Menu(
            menuGroup = menuGroup(),
            name = "양념치킨",
            price = price,
            displayStatus = displayStatus,
            menuProducts = menuProducts,
            menuNameValidatorService,
            menuPriceValidatorService
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
        quantity: MenuProductQuantity = MenuProductQuantity(1)
    ): MenuProduct {
        return MenuProduct(productId = productId, quantity = quantity)
    }
}
