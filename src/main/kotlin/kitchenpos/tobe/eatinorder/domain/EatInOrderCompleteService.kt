package kitchenpos.tobe.eatinorder.domain

import kitchenpos.order.common.domain.OrderStatus
import kitchenpos.tobe.eatinorder.domain.repository.EatInOrderRepository
import kitchenpos.tobe.support.DomainService
import java.util.*

@DomainService
class EatInOrderCompleteService(
    private val eatInOrderRepository: EatInOrderRepository,
) {
    /***
     * 주문 테이블의 모든 주문이 완료됐으면
     * 테이블을 초기화한다.
     */
    fun complete(orderId: UUID): UUID {
        val eatInOrder = eatInOrderRepository.findEatInOrderById(orderId)
        eatInOrder.complete()
        val orderTable = eatInOrderRepository.findEatInOrderById(orderId).orderTable
        if (
            eatInOrderRepository.existsByOrderTableAndStatusNot(
                orderTable,
                OrderStatus.COMPLETED,
            ).not()
        ) {
            orderTable.clear()
        }

        return eatInOrder.id
    }
}
