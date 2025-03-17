package kitchenpos.order.tobe.eatinorder.application

import kitchenpos.order.tobe.eatinorder.domain.EatInOrderCompleteEvent
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class OrderTableEatInOrderCompleteEventListener(
    private val eatInOrderRepository: EatInOrderRepository,
    private val orderTableRepository: OrderTableRepository
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handle(event: EatInOrderCompleteEvent) {
        val eatInOrder = eatInOrderRepository.findById(event.eatInOrderId).orElseThrow {
            IllegalArgumentException("주문을 찾을 수 없습니다.")
        }
        val orderTable = orderTableRepository.findById(eatInOrder.orderTableId).orElseThrow {
            IllegalArgumentException("주문 테이블을 찾을 수 없습니다.")
        }
        orderTable.empty()
    }
}
