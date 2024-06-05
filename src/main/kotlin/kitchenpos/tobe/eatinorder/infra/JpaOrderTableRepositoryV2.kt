package kitchenpos.tobe.eatinorder.infra

import kitchenpos.tobe.eatinorder.domain.entity.OrderTableV2
import kitchenpos.tobe.eatinorder.domain.repository.OrderTableRepositoryV2
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaOrderTableRepositoryV2 : JpaRepository<OrderTableV2, UUID>, OrderTableRepositoryV2
