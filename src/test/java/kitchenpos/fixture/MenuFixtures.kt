package kitchenpos.fixture

import kitchenpos.common.Price
import kitchenpos.common.price
import kitchenpos.menus.application.tobe.FakeMenuNameValidatorService
import kitchenpos.menus.application.tobe.FakeMenuPriceValidatorService
import kitchenpos.menus.tobe.domain.*
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
