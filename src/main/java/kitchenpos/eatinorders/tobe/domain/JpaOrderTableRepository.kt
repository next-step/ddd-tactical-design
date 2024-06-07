package kitchenpos.eatinorders.tobe.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaOrderTableRepository : JpaRepository<OrderTable, UUID>, OrderTableRepository {
}
