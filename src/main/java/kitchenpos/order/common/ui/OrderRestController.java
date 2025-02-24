package kitchenpos.order.common.ui;

import java.util.List;
import kitchenpos.order.common.application.OrderService;
import kitchenpos.order.common.domain.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/orders")
@RestController
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
