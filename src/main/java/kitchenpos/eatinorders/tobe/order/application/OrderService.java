package kitchenpos.eatinorders.tobe.order.application;

import kitchenpos.eatinorders.tobe.order.domain.Order;
import kitchenpos.eatinorders.tobe.order.domain.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAllByTableIdIn(List<Long> tableIds) {

        if (CollectionUtils.isEmpty(tableIds)) {
            throw new IllegalArgumentException("tableId를 입력해야합니다.");
        }

        if (tableIds.size() != tableIds.stream()
                .distinct()
                .collect(Collectors.toList())
                .size()) {
            throw new IllegalArgumentException("중복된 tableId를 입력할 수 없습니다.");
        }

        final List<Order> orders = orderRepository.findAllByTableIdIn(tableIds);

        return orders;
    }
}
