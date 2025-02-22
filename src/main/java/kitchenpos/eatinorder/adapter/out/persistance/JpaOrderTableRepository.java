package kitchenpos.eatinorder.adapter.out.persistance;

import kitchenpos.eatinorder.application.port.out.EatInOrderTableRepository;
import kitchenpos.eatinorder.domain.EatInOrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderTableRepository extends EatInOrderTableRepository, JpaRepository<EatInOrderTable, UUID> {
}
