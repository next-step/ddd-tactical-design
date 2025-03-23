package kitchenpos.order.tobe.eatinorder.infra

import java.util.*
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableRepository
import org.springframework.data.jpa.repository.JpaRepository

interface TobeJpaOrderTableRepository : OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
