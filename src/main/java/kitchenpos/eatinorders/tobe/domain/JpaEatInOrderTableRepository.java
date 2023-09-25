package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderTableRepository extends EatInOrderTableRepository, JpaRepository<EatInOrderTable, UUID> {
}
