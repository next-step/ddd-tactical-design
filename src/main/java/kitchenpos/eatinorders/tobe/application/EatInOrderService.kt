package kitchenpos.eatinorders.tobe.application

import kitchenpos.eatinorders.tobe.domain.*
import kitchenpos.eatinorders.tobe.dto.CreateOrderRequest
import kitchenpos.eatinorders.tobe.dto.MenuValidateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Service
class EatInOrderService(
    private val eatInOrderRepository: EatInOrderRepository,
    private val orderTableServiceV2: OrderTableServiceV2,
    private val menuValidator: MenuValidator,
) {
    fun create(
        request: CreateOrderRequest,
    ): EatInOrder {
        val orderLineItems = request.orderLineItems.map {
            OrderLineItem(
                orderMenu = OrderMenu(
                    menuId = it.menuInfo.menuId,
                    price = it.menuInfo.price,
                ),
                quantity = it.quantity,
            )
        }

        val menuValidateRequests = orderLineItems.map {
            MenuValidateRequest(
                menuId = it.orderMenu.menuId,
                price = it.orderMenu.price,
            )
        }

        menuValidator.canOrder(menuValidateRequests)

        val order = EatInOrder(

            orderLineItems = OrderLineItems(orderLineItems),
            orderTable = orderTableServiceV2.getOrderTable(request.orderTableId),
        )

        return eatInOrderRepository.save(order)
    }

    fun accept(
        orderId: UUID,
    ): EatInOrder {
        val order = getOrder(orderId)
        order.accept()
        return order
    }

    fun serve(
        orderId: UUID,
    ): EatInOrder {
        val order = getOrder(orderId)
        order.served()
        return order
    }

    fun complete(
        orderId: UUID,
    ): EatInOrder {
        val order = getOrder(orderId)
        order.complete()
        return order
    }

    @Transactional(readOnly = true)
    fun findAll() = eatInOrderRepository.findAll()

    private fun getOrder(eatInOrderId: UUID) =
        eatInOrderRepository.findByIdOrNull(eatInOrderId) ?: throw NoSuchElementException()
}
