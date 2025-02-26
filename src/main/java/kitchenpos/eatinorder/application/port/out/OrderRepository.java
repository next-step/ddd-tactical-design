package kitchenpos.eatinorder.application.port.out;

import kitchenpos.eatinorder.domain.model.Order;
import kitchenpos.eatinorder.domain.model.OrderStatus;
import kitchenpos.eatinorder.domain.model.OrderTableEntity;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    @Query("select count(o) > 0 from Order o where o.orderTableId = :orderTableId and o.status <> :status")
    boolean existsByOrderTableAndStatusNot(@Param("orderTableId") UUID orderTableId, @Param("status") EatInOrderStatus status);
}

