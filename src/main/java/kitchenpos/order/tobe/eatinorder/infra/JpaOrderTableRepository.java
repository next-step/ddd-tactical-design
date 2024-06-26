package kitchenpos.order.tobe.eatinorder.infra;


import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTable;
import kitchenpos.order.tobe.eatinorder.domain.ordertable.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("newOrderTableRepository")
public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, UUID> {
}
