package kitchenpos.order.tobe.eatinorder.infra

import java.util.*
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderOrderTableClient
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderOrderTableInfo
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository
import org.springframework.stereotype.Service

@Service
class DefaultEatInOrderOrderTableClient(
    private val orderTableRepository: OrderTableRepository,
) : EatInOrderOrderTableClient {
    override fun getOrderTable(orderTableId: UUID): EatInOrderOrderTableInfo {
        return orderTableRepository.findById(orderTableId).orElseThrow()
            .let { orderTable ->
                EatInOrderOrderTableInfo(
                    orderTableId = orderTable.id,
                    orderTableStatus = orderTable.orderTableOccupancy.status
                )
            }
    }
}
