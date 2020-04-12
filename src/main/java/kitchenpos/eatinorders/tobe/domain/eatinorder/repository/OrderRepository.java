package kitchenpos.eatinorders.tobe.domain.eatinorder.repository;

import kitchenpos.eatinorders.model.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.eatinorder.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders o WHERE o.order_table_id = :orderTableId", nativeQuery = true)
    Order findByOrderTableId(@Param("orderTableId") Long orderTableId);

    @Query(value = "SELECT COUNT(SELECT * FROM orders o WHERE o.order_table_id in (:orderTableIds) and o.order_status != :orderStatus) from orders"
            , nativeQuery = true)
    int countByOrderTableIdsAndNotEqaulOrderStatus(@Param("orderTableIds") List<Long> orderTableIds, @Param("orderStatus")OrderStatus orderStatus);
}
