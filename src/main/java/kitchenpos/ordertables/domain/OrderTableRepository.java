package kitchenpos.ordertables.domain;

import kitchenpos.eatinorders.domain.EatInOrderStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderTableRepository {
    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(OrderTableId id);

    List<OrderTable> findAll();

    boolean existsByOrderAndStatusNot(OrderTableId orderTableId,EatInOrderStatus status);


}

