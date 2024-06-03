package kitchenpos.eatinorders.tobe.domain

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaOrderTableRepositoryV2 : OrderTableRepositoryV2, JpaRepository<OrderTable, UUID>
