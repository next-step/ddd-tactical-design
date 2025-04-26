package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTable;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderTableRepository extends EatInOrderTableRepository, JpaRepository<EatInOrderTable, UUID> {
}
