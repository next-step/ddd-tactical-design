package kitchenpos.fixture

import kitchenpos.common.Price
import kitchenpos.eatinorders.tobe.AlwaysSuccessEatInOrderCreateValidator
import kitchenpos.eatinorders.tobe.domain.*
import java.math.BigDecimal
import java.util.UUID

object EatInOrderFixtures {
    fun orderTable(): OrderTable {
        return OrderTable.of(OrderTableName("1번"))
    }

    fun waitingEatInOrder(
        orderTableId: UUID = UUID.randomUUID(),
        eatInOrderLineItems: EatInOrderLineItems = EatInOrderLineItems(listOf(eatInOrderLineItem()))
    ): EatInOrder {
        return EatInOrder.of(
            orderTableId = orderTableId,
            eatInOrderLineItems = eatInOrderLineItems,
            eatInOrderCreateValidator = AlwaysSuccessEatInOrderCreateValidator
        )
    }

    fun acceptedEatInOrder(
        orderTableId: UUID = UUID.randomUUID(),
        eatInOrderLineItems: EatInOrderLineItems
    ): EatInOrder {
        val eatInOrder = waitingEatInOrder(orderTableId = orderTableId, eatInOrderLineItems = eatInOrderLineItems)

        eatInOrder.accept()

        return eatInOrder
    }

    fun servedEatInOrder(orderTableId: UUID = UUID.randomUUID(), eatInOrderLineItems: EatInOrderLineItems): EatInOrder {
        val eatInOrder = acceptedEatInOrder(orderTableId = orderTableId, eatInOrderLineItems = eatInOrderLineItems)

        eatInOrder.serve()

        return eatInOrder
    }

    fun completedEatInOrder(
        orderTableId: UUID = UUID.randomUUID(),
        eatInOrderLineItems: EatInOrderLineItems
    ): EatInOrder {
        val eatInOrder = servedEatInOrder(orderTableId = orderTableId, eatInOrderLineItems = eatInOrderLineItems)

        eatInOrder.complete()

        return eatInOrder
    }

    fun eatInOrderLineItem(
        quantity: EatInOrderLineItemQuantity = EatInOrderLineItemQuantity(1),
        price: Price = Price(BigDecimal.valueOf(5000)),
        menuId: UUID = UUID.randomUUID()
    ): EatInOrderLineItem {
        return EatInOrderLineItem(quantity = quantity, price = price, menuId = menuId)
    }
}
