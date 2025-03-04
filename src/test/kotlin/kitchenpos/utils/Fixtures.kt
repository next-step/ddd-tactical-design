package kitchenpos.utils

import java.util.*
import kitchenpos.menu.domain.MenuGroup
import kitchenpos.menu.tobe.domain.Menu
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
            displayed: Boolean,
            menuGroup: MenuGroup = menuGroup(),
            menuProducts: MenuProducts = MenuProducts(listOf()),
        ): Menu {
            return Menu(
                id = id,
                name = name,
                price = price.toBigDecimal(),
                displayed = displayed,
                menuGroup = menuGroup,
                menuProducts = menuProducts,
            )
        }

        fun menuProduct(
            product: Product,
            quantity: Long,
        ): MenuProduct {
            return MenuProduct(
                product = product,
                quantity = quantity
            )
        }

        fun menuGroup(
            id: UUID = UUID.randomUUID(),
            name: String = "두마리메뉴",
        ): MenuGroup {
            val menuGroup = MenuGroup()
            menuGroup.id = id
            menuGroup.name = name
            return menuGroup
        }
    }
}
