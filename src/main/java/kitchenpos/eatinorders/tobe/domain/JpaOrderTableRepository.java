package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderTableRepository extends JpaRepository<OrderTable, UUID> {

}
