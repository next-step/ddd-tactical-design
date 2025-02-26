package kitchenpos.utils

import java.util.*
import kitchenpos.menu.domain.MenuGroup
import kitchenpos.menu.tobe.domain.Menu
import kitchenpos.menu.tobe.domain.MenuProduct
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductName
import kitchenpos.product.tobe.domain.ProductNamePolicy
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
                price = price.toBigDecimal()
            )
        }

        fun menu(
            id: UUID = UUID.randomUUID(),
            name: String,
            price: Long,
            displayed: Boolean,
            menuGroup: MenuGroup = menuGroup(),
            menuProducts: List<MenuProduct> = emptyList()
        ): Menu {
            return Menu(
                id = id,
                name = name,
                price = price.toBigDecimal(),
                displayed = displayed,
                menuGroup = menuGroup,
                menuProducts = menuProducts,
                menuGroupId = menuGroup.id
            )
        }

        fun menuProduct(
            product: Product,
            quantity: Long,
        ): MenuProduct {
            return MenuProduct(
                product = product,
                quantity = quantity,
                productId = product.id!!
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
