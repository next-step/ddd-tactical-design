package kitchenpos.eatinorders.tobe.domain

import java.util.*

fun EatInOrderRepository.findByIdOrNull(eatInOrderId: UUID) = findById(eatInOrderId).orElse(null)

interface EatInOrderRepository {

    fun save(eatInOrder: EatInOrder): EatInOrder

    fun existsByOrderTableIdAndStatusNot(orderTableId: UUID, status: EatInOrderStatus): Boolean

    fun findById(eatInOrderId: UUID): Optional<EatInOrder>

}
