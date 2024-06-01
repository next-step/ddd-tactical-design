package kitchenpos.eatinorders.tobe.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaEatInOrderRepository : EatInOrderRepository, JpaRepository<EatInOrder, UUID>
