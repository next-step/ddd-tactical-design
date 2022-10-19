package kitchenpos.eatinordertables.infra;

import java.util.UUID;
import kitchenpos.eatinordertables.domain.EatInOrderTable;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEatInOrderTableRepository extends EatInOrderTableRepository, JpaRepository<EatInOrderTable, UUID> {
}
