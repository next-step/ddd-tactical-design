package kitchenpos.tobe.eatinorder.application

import kitchenpos.tobe.eatinorder.application.dto.request.CreateOrderRequest
import kitchenpos.tobe.eatinorder.application.dto.response.CreateEatInOrderResponse
import kitchenpos.tobe.eatinorder.application.dto.response.GetOrderResponse
import kitchenpos.tobe.eatinorder.domain.EatInOrderCompleteService
import kitchenpos.tobe.eatinorder.domain.MenuClient
import kitchenpos.tobe.eatinorder.domain.entity.EatInOrder
import kitchenpos.tobe.eatinorder.domain.entity.OrderLineItemV2
import kitchenpos.tobe.eatinorder.domain.entity.OrderTableV2
import kitchenpos.tobe.eatinorder.domain.repository.EatInOrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class EatInOrderService(
    private val menuClient: MenuClient,
    private val eatInOrderRepository: EatInOrderRepository,
    private val eatInOrderCompleteService: EatInOrderCompleteService,
) {
    fun createOrder(request: CreateOrderRequest): CreateEatInOrderResponse {
        val menus = menuClient.getMenus(request.orderLineItems.map { it.menuId })
        require(menus.all { it.displayed }) { "미노출 메뉴가 존재합니다." }

        val order =
            EatInOrder.of(
                orderDateTime = LocalDateTime.now(),
                orderLineItems =
                    request.orderLineItems.map { orderLineItem ->
                        OrderLineItemV2(
                            menuId = orderLineItem.menuId,
                            quantity = orderLineItem.quantity,
                            price = orderLineItem.price,
                        )
                    },
                orderTable =
                    OrderTableV2(
                        id = request.orderTable.id,
                        name = request.orderTable.name,
                        numberOfGuests = request.orderTable.numberOfGuests,
                    ),
            )

        return CreateEatInOrderResponse.from(eatInOrderRepository.save(order))
    }

    fun accept(orderId: UUID): UUID {
        val eatInOrder = eatInOrderRepository.findEatInOrderById(orderId)
        eatInOrder.accept()
        return eatInOrder.id
    }

    fun serve(orderId: UUID): UUID {
        val eatInOrder = eatInOrderRepository.findEatInOrderById(orderId)
        eatInOrder.serve()
        return eatInOrder.id
    }

    fun complete(orderId: UUID): UUID {
        return eatInOrderCompleteService.complete(orderId)
    }

    fun findAll(): List<GetOrderResponse> {
        return eatInOrderRepository.findAll().map { GetOrderResponse.from(it) }
    }
}
