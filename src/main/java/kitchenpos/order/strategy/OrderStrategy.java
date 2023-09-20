package kitchenpos.order.strategy;

import kitchenpos.order.domain.OrderType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderStrategy {

    final List<OrderProcess> orderTypes;

    public OrderStrategy(List<OrderProcess> orderProcesses) {
        this.orderTypes = orderProcesses;
    }

    public OrderProcess get(OrderType orderType) {
        return orderTypes.stream()
                .filter(orderProcess -> orderProcess.support(orderType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 주문 타입입니다."));
    }
}
