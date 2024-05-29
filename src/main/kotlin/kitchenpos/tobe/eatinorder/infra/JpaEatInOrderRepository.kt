package kitchenpos.tobe.eatinorder.infra

import kitchenpos.tobe.eatinorder.domain.entity.EatInOrder
import kitchenpos.tobe.eatinorder.domain.repository.EatInOrderRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaEatInOrderRepository : JpaRepository<EatInOrder, Long>, EatInOrderRepository
