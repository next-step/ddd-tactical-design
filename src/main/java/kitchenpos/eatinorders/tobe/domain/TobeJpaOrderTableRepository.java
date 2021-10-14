package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TobeJpaOrderTableRepository extends TobeOrderTableRepository, JpaRepository<TobeOrderTable, UUID> {
}
