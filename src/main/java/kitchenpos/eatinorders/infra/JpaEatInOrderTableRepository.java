package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.EatInOrderTable;
import kitchenpos.eatinorders.domain.EatInOrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderTableRepository extends EatInOrderTableRepository, JpaRepository<EatInOrderTable, UUID> {
}
