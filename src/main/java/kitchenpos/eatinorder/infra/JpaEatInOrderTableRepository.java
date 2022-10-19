package kitchenpos.eatinorder.infra;

import java.util.UUID;
import kitchenpos.eatinorder.domain.EatInOrderTable;
import kitchenpos.eatinorder.domain.EatInOrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEatInOrderTableRepository extends EatInOrderTableRepository,
    JpaRepository<EatInOrderTable, UUID> {

}
