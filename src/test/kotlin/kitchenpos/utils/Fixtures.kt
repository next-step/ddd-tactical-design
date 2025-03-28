package kitchenpos.utils

import java.util.*
import kitchenpos.menu.tobe.domain.Menu
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.menu.tobe.domain.MenuGroup
import kitchenpos.menu.tobe.domain.MenuName
import kitchenpos.menu.tobe.domain.MenuNamePolicy
import kitchenpos.menu.tobe.domain.MenuPrice
import kitchenpos.menu.tobe.domain.MenuProduct
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.menu.tobe.domain.ProductInfo
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItem
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItems
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableName
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableOccupancy
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableStatus
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
            productInfos: Map<UUID, ProductInfo>,
            name: String = "후라이드1마리",
            price: Long,
            display: MenuDisplay = MenuDisplay.DISPLAYED,
            menuGroup: MenuGroup = menuGroup(),
            menuProducts: MenuProducts = MenuProducts(listOf(menuProduct())),
        ): Menu {
            return Menu(
                productInfos = productInfos,
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

        fun orderTable(
            id: UUID = UUID.randomUUID(),
            name: OrderTableName = OrderTableName("테이블1"),
            orderTableOccupancy: OrderTableOccupancy = OrderTableOccupancy(0, OrderTableStatus.EMPTY)
        ): OrderTable {
            return OrderTable(
                id = id,
                orderTableName = name,
                orderTableOccupancy = orderTableOccupancy
            )
        }

        fun eatInOrderLineItems(
            menuId: UUID,
        ): EatInOrderLineItems {
            return EatInOrderLineItems(
                listOf(
                    EatInOrderLineItem(
                        seq = 1,
                        menuId = menuId,
                        quantity = 1,
                    )
                )
            )
        }

        fun eatInOrder(
            menuId: UUID,
            orderTableId: UUID,
            status: EatInOrderStatus,
        ): EatInOrder {
            return eatInOrder(
                orderLineItems = EatInOrderLineItems(
                    listOf(
                        EatInOrderLineItem(1, 1, menuId)
                    )
                ),
                orderTableId = orderTableId,
                status = status
            )
        }

        private fun eatInOrder(
            orderLineItems: EatInOrderLineItems,
            orderTableId: UUID = UUID.randomUUID(),
            status: EatInOrderStatus,
            ): EatInOrder {
            return EatInOrder(
                orderLineItems = orderLineItems,
                orderTableId = orderTableId,
                status = status
            )
        }
    }
}
