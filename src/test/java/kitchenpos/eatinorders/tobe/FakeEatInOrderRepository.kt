package kitchenpos.eatinorders.tobe

import kitchenpos.eatinorders.tobe.domain.EatInOrder
import kitchenpos.eatinorders.tobe.domain.EatInOrderRepository
import kitchenpos.eatinorders.tobe.domain.EatInOrderStatus
import java.util.*

object FakeEatInOrderRepository : EatInOrderRepository {
    private val eatInOrderMap: MutableMap<UUID, EatInOrder> = mutableMapOf()

    override fun save(eatInOrder: EatInOrder): EatInOrder {
        eatInOrderMap[eatInOrder.id] = eatInOrder

        return eatInOrder
    }

    override fun existsByOrderTableIdAndStatusNot(orderTableId: UUID, status: EatInOrderStatus): Boolean {
        return eatInOrderMap.filterValues { it.status != status }.isNotEmpty()
    }

    override fun findById(eatInOrderId: UUID): Optional<EatInOrder> {
        return Optional.ofNullable(eatInOrderMap[eatInOrderId])
    }
}
