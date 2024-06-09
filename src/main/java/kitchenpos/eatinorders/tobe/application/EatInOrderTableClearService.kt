package kitchenpos.eatinorders.tobe.application

import kitchenpos.eatinorders.tobe.domain.*
import kitchenpos.eatinorders.tobe.event.EatInOrderCompleted
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class EatInOrderTableClearService(
    private val orderTableClearValidator: OrderTableClearValidator,
    private val orderTableRepository: OrderTableRepository,
    private val eatInOrderRepository: EatInOrderRepository
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun clearOrderTable(eatInOrderCompleted: EatInOrderCompleted) {
        val eatInOrder = eatInOrderRepository.findByIdOrNull(eatInOrderCompleted.eatInOrderId)
            ?: throw NoSuchElementException("can not find order: ${eatInOrderCompleted.eatInOrderId}")

        val eatInOrderTable = orderTableRepository.findByIdOrNull(eatInOrder.orderTableId)
            ?: throw NoSuchElementException("can not find order table: ${eatInOrder.orderTableId}")

        eatInOrderTable.clear(orderTableClearValidator)
    }
}
