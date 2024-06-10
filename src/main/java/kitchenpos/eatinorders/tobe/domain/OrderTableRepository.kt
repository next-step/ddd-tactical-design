package kitchenpos.eatinorders.tobe.domain

import java.util.Optional
import java.util.UUID


fun OrderTableRepository.findByIdOrNull(orderTableId: UUID): OrderTable? = findById(orderTableId).orElse(null)

interface OrderTableRepository {
    fun save(orderTable: OrderTable): OrderTable

    fun findById(orderTableId: UUID): Optional<OrderTable>
}
