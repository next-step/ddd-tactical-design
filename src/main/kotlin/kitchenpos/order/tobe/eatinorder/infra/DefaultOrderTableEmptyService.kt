package kitchenpos.order.tobe.eatinorder.infra

import kitchenpos.common.annotation.DomainService
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableEmptyService

@DomainService
class DefaultOrderTableEmptyService(
    private val eatInOrderRepository: EatInOrderRepository,
) : OrderTableEmptyService {

    override fun canEmpty(orderTable: OrderTable): Boolean {
        return !eatInOrderRepository.existsByOrderTableIdAndStatusNot(
            orderTable.id,
            EatInOrderStatus.COMPLETED
        )
    }
}
