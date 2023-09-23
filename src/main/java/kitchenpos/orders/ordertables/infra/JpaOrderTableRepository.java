package kitchenpos.orders.ordertables.infra;

import kitchenpos.orders.eatinorders.domain.EatInOrderStatus;
import kitchenpos.orders.ordertables.domain.OrderTableRepository;
import kitchenpos.orders.ordertables.domain.OrderTable;
import kitchenpos.orders.ordertables.domain.OrderTableId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaOrderTableRepository extends OrderTableRepository, JpaRepository<OrderTable, OrderTableId> {

    @Query("select count(o) < 0 from OrderTable t join EatInOrder o on t.id = o.orderTableId and o.orderTableId = :orderTableId and o.status = :status")
    @Override
    boolean existsByOrderAndStatusNot(OrderTableId orderTableId, EatInOrderStatus status);
}
