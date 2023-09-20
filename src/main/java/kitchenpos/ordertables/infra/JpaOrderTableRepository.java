package kitchenpos.ordertables.infra;

import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableId;
import kitchenpos.ordertables.domain.OrderTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, OrderTableId> {

    @Query("select count(o) < 0 from OrderTable t join EatInOrder o on t.id = o.orderTableId and o.orderTableId = :orderTableId and o.status = :status")
    @Override
    boolean existsByOrderAndStatusNot(OrderTableId orderTableId, EatInOrderStatus status);
}
