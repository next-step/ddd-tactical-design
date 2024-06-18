package kitchenpos.eatinorders.tobe.repository;

import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TobeJpaOrderTableRepository extends TobeOrderTableRepository, JpaRepository<TobeOrderTable, UUID> {
}
