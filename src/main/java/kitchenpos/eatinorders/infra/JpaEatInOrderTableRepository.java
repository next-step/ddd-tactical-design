package kitchenpos.eatinorders.infra;

import java.util.UUID;
import kitchenpos.eatinorders.domain.EatInOrderTable;
import kitchenpos.eatinorders.domain.EatInOrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEatInOrderTableRepository
    extends EatInOrderTableRepository, JpaRepository<EatInOrderTable, UUID> {}
