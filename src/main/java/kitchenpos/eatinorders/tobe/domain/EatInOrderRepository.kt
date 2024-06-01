package kitchenpos.eatinorders.tobe.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EatInOrderRepository : JpaRepository<EatInOrder, UUID>
