package kitchenpos.utils

import kitchenpos.menu.domain.Menu
import kitchenpos.menu.domain.MenuGroup
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductName
import kitchenpos.product.tobe.infra.FakeProfanities
import java.util.*

class Fixtures {
    companion object {
        fun product(
            id: UUID = UUID.randomUUID(),
            name: String,
            price: Long
        ): Product {
            return Product(
                id = id,
                productName = ProductName(FakeProfanities(), name),
                price = price.toBigDecimal()
            )
        }

        fun menu(
            id: UUID = UUID.randomUUID(),
            name: String,
            price: Long,
            displayed: Boolean,
            menuGroup: MenuGroup = menuGroup(),
        ): Menu {
            val menu = Menu()
            menu.id = id
            menu.name = name
            menu.price = price.toBigDecimal()
            menu.isDisplayed = displayed
            menu.menuGroup = menuGroup
            return menu
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
