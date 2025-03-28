package kitchenpos.order.tobe.eatinorder.infra

import java.util.*
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository
import org.springframework.data.jpa.repository.JpaRepository

interface TobeJpaEatInOrderRepository : EatInOrderRepository, JpaRepository<EatInOrder, UUID>
