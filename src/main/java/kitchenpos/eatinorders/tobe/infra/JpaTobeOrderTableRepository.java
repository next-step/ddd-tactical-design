package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.application.TobeOrderTable;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTobeOrderTableRepository extends TobeOrderTableRepository, JpaRepository<TobeOrderTable, UUID> {
}
