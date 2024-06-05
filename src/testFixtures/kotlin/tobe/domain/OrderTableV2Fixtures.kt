package tobe.domain

import kitchenpos.tobe.eatinorder.domain.entity.OrderTableV2
import java.util.*

object OrderTableV2Fixtures {
    fun createOrderTableV2(): OrderTableV2 {
        return OrderTableV2.of(
            id = UUID.randomUUID(),
            name = "1번 테이블",
            numberOfGuests = 4,
            occupied = false,
        )
    }
}
