package kitchenpos.utils

import java.util.*
import kitchenpos.menu.tobe.domain.MenuGroup
import kitchenpos.menu.tobe.domain.Menu
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.menu.tobe.domain.MenuName
import kitchenpos.menu.tobe.domain.MenuNamePolicy
import kitchenpos.menu.tobe.domain.MenuPrice
import kitchenpos.menu.tobe.domain.MenuProduct
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductName
import kitchenpos.product.tobe.domain.ProductNamePolicy
import kitchenpos.product.tobe.domain.ProductPrice
import kitchenpos.product.tobe.domain.ProductPricePolicy
import kitchenpos.product.tobe.infra.FakeProfanities

class Fixtures {
    companion object {
        val INVALID_UUID = UUID(0L, 0L);

        fun product(
            id: UUID = UUID.randomUUID(),
            name: String,
            price: Long
        ): Product {
            return Product(
                id = id,
                productName = ProductName(ProductNamePolicy(FakeProfanities()), name),
                productPrice = ProductPrice(ProductPricePolicy(), price.toBigDecimal())
            )
        }

        fun menu(
            id: UUID = UUID.randomUUID(),
            name: String,
            price: Long,
            display: MenuDisplay,
            menuGroup: MenuGroup = menuGroup(),
            menuProducts: MenuProducts = MenuProducts(listOf()),
        ): Menu {
            return Menu(
                id = id,
                menuName = MenuName(MenuNamePolicy(FakeProfanities()), name),
                menuPrice = MenuPrice(price.toBigDecimal()),
                menuDisplay = display,
                menuGroup = menuGroup,
                menuProducts = menuProducts,
            )
        }

        fun menuProduct(
            product: Product,
            quantity: Long,
        ): MenuProduct {
            return MenuProduct(
                productId = product.id,
                quantity = quantity
            )
        }

        fun menuProduct(
            productId: UUID,
            quantity: Long,
        ): MenuProduct {
            return MenuProduct(
                productId = productId,
                quantity = quantity
            )
        }

        fun menuGroup(
            id: UUID = UUID.randomUUID(),
            name: String = "두마리메뉴",
        ): MenuGroup {
            val menuGroup = MenuGroup(id, name)
            return menuGroup
        }
    }
}
