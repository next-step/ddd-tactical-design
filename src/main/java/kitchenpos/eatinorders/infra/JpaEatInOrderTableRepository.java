package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.EatInOrderTable;
import kitchenpos.eatinorders.tobe.domain.EatInOrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderTableRepository extends EatInOrderTableRepository, JpaRepository<EatInOrderTable, UUID> {
}
