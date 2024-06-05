package kitchenpos.tobe.eatinorder.domain.repository

import kitchenpos.tobe.eatinorder.domain.entity.OrderTableV2
import java.util.UUID

interface OrderTableRepositoryV2 {
    fun save(orderTable: OrderTableV2): OrderTableV2

    fun findOrderTableById(orderTableId: UUID): OrderTableV2?
}
