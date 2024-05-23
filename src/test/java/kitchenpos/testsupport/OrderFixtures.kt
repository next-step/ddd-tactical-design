package kitchenpos.testsupport

import java.math.BigDecimal
import java.util.UUID
import kitchenpos.eatinorders.domain.Order
import kitchenpos.eatinorders.domain.OrderLineItem
import kitchenpos.eatinorders.domain.OrderStatus
import kitchenpos.eatinorders.domain.OrderTable
import kitchenpos.eatinorders.domain.OrderType
import kitchenpos.menus.domain.Menu

object OrderFixtures {
    fun createOrder(
        type: OrderType,
        status: OrderStatus,
        menus: List<Menu>,
        orderLineItemRequestPrice: BigDecimal? = null,
        quantity: Long = 1,
        deliveryAddress: String? = null,
        orderTable: OrderTable? = null
    ): Order {
        return Order().apply {
            this.id = UUID.randomUUID()
            this.type = type
            this.status = status
            this.orderLineItems = menus.map { menu ->
                OrderLineItem().also {
                    it.menu = menu
                    it.menuId = menu.id
                    it.price = orderLineItemRequestPrice ?: menu.price
                    it.quantity = quantity
                }
            }
            this.deliveryAddress = deliveryAddress
            this.orderTableId = orderTable?.id
            this.orderTable = orderTable
        }
    }
}