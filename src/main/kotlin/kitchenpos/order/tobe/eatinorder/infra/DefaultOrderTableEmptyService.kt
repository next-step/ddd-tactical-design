package kitchenpos.order.tobe.eatinorder.infra

import kitchenpos.common.annotation.DomainService
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus
import kitchenpos.order.tobe.eatinorder.domain.OrderTable
import kitchenpos.order.tobe.eatinorder.domain.OrderTableEmptyService

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
