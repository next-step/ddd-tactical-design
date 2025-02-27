package kitchenpos.eatinorder.adapter.out.persistance;

import kitchenpos.eatinorder.adapter.out.persistance.entity.Order;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<Order, UUID> {
    @Query("select count(o) > 0 from Order o where o.orderTableId = :orderTableId and o.status <> :status")
    boolean existsByOrderTableAndStatusNot(@Param("orderTableId") UUID orderTableId, @Param("status") EatInOrderStatus status);
}
