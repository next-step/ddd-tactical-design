package kitchenpos.utils

import java.util.*
import kitchenpos.menu.tobe.domain.Menu
import kitchenpos.menu.tobe.domain.MenuAmountService
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.menu.tobe.domain.MenuGroup
import kitchenpos.menu.tobe.domain.MenuName
import kitchenpos.menu.tobe.domain.MenuNamePolicy
import kitchenpos.menu.tobe.domain.MenuPrice
import kitchenpos.menu.tobe.domain.MenuProduct
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductName
import kitchenpos.product.tobe.domain.ProductNamePolicy
import kitchenpos.product.tobe.domain.ProductPrice
import kitchenpos.product.tobe.infra.FakeProfanities

class Fixtures {
    companion object {
        val INVALID_UUID = UUID(0L, 0L);

        fun product(
            name: String = "후라이드",
            price: Long
        ): Product {
            return Product(
                productName = ProductName(ProductNamePolicy(FakeProfanities()), name),
                productPrice = ProductPrice(price.toBigDecimal())
            )
        }

        fun menu(
            menuAmountService: MenuAmountService,
            name: String = "후라이드1마리",
            price: Long,
            display: MenuDisplay = MenuDisplay.DISPLAYED,
            menuGroup: MenuGroup = menuGroup(),
            menuProducts: MenuProducts = MenuProducts(listOf(menuProduct())),
        ): Menu {
            return Menu(
                menuAmountService = menuAmountService,
                menuName = MenuName(MenuNamePolicy(FakeProfanities()), name),
                menuPrice = MenuPrice(price.toBigDecimal()),
                menuDisplay = display,
                menuGroup = menuGroup,
                menuProducts = menuProducts,
            )
        }

        fun menuProduct(
            seq: Long = 1,
            productId: UUID = UUID.randomUUID(),
            quantity: Long = 1,
        ): MenuProduct {
            return MenuProduct(
                seq = seq,
                productId = productId,
                quantity = quantity
            )
        }


        fun menuGroup(
            name: String = "추천메뉴",
        ): MenuGroup {
            val menuGroup = MenuGroup(name = name)
            return menuGroup
        }
    }
}
