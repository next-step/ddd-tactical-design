package kitchenpos.eatinorders.tobe.domain

import java.util.*

interface EatInOrderRepository {
    fun save(eatInOrder: EatInOrder): EatInOrder
    fun findByIdOrNull(eatInOrderId: UUID): EatInOrder?
    fun findAll(): List<EatInOrder>
}
