package kitchenpos.eatinorders.tobe.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaEatInOrderRepository : JpaRepository<EatInOrder, UUID>, EatInOrderRepository {
    override fun existsByOrderTableIdAndStatusNot(orderTableId: UUID, status: EatInOrderStatus): Boolean
}
