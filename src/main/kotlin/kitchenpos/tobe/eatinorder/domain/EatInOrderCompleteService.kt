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
        val orderTable = eatInOrderRepository.findEatInOrderById(orderId).orderTable
        val isAllCompleted =
            eatInOrderRepository.existsByOrderTableAndStatusNot(
                orderTable,
                OrderStatus.COMPLETED,
            ).not()

        check(isAllCompleted) { "주문 테이블의 모든 주문이 완료되지 않았습니다." }

        val eatInOrder = eatInOrderRepository.findEatInOrderById(orderId)
        eatInOrder.complete()
        orderTable.clear()

        return eatInOrder.id
    }
}
