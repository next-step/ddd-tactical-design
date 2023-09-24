package kitchenpos.order.supports.strategy;

import kitchenpos.order.domain.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProcessStrategy {

    final List<OrderProcess> orderTypes;

    public OrderProcessStrategy(List<OrderProcess> orderProcesses) {
        this.orderTypes = orderProcesses;
    }

    public OrderProcess get(Order order) {
        validOrderType(order);
        return orderTypes.stream()
                .filter(orderProcess -> orderProcess.support(order.getType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 주문 타입입니다."));
    }

    private void validOrderType(Order order) {
        if (order == null) throw new IllegalArgumentException("주문이 존재하지 않습니다.");
        if (order.getType() == null) throw new IllegalArgumentException("주문 타입이 존재하지 않습니다.");
    }
}
