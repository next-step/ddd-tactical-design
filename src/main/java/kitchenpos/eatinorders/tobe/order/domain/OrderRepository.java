package kitchenpos.eatinorders.tobe.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByTableIdIn(List<Long> tableIds);
}
