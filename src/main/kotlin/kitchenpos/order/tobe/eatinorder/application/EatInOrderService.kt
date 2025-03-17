package kitchenpos.order.tobe.eatinorder.application

import java.util.*
import kitchenpos.order.tobe.common.OrderMenuClient
import kitchenpos.order.tobe.eatinorder.application.dto.CreateEatInOrderReq
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItem
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItems
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderOrderTableClient
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("tobeEatInOrderService")
class EatInOrderService(
    private val eatInOrderRepository: EatInOrderRepository,
    private val orderMenuClient: OrderMenuClient,
    private val eatInOrderOrderTableClient: EatInOrderOrderTableClient,
) {

    @Transactional
    fun create(req: CreateEatInOrderReq): UUID {
        val orderLineItems = req.orderLineItems.map { orderLineItem ->
            EatInOrderLineItem.create(
                seq = orderLineItem.seq,
                menuInfo = orderMenuClient.getMenu(orderLineItem.menuId),
                quantity = orderLineItem.quantity,
            )
        }
        val eatInOrder = EatInOrder.create(
            orderTableInfo = eatInOrderOrderTableClient.getOrderTable(req.orderTableId),
            orderLineItems = EatInOrderLineItems(orderLineItems),
        )
        return eatInOrderRepository.save(eatInOrder).id
    }

    @Transactional
    fun accept(orderId: UUID) {
        val eatInOrder = eatInOrderRepository.findById(orderId).orElseThrow {
            NoSuchElementException("주문이 존재하지 않습니다.")
        }
        eatInOrder.accept()
    }

    @Transactional
    fun serve(orderId: UUID) {
        val eatInOrder = eatInOrderRepository.findById(orderId).orElseThrow {
            NoSuchElementException("주문이 존재하지 않습니다.")
        }
        eatInOrder.serve()
    }

    @Transactional
    fun complete(orderId: UUID) {
        val eatInOrder = eatInOrderRepository.findById(orderId).orElseThrow {
            NoSuchElementException("주문이 존재하지 않습니다.")
        }
        eatInOrder.complete()
        eatInOrderRepository.save(eatInOrder)
    }
}
