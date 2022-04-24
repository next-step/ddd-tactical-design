package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TobeJpaOrderTableRepository extends TobeOrderTableRepository, JpaRepository<TobeOrderTable, OrderTableId> {
}
