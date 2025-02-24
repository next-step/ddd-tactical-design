package kitchenpos.order.eatinorder.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.common.application.OrderService;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.eatinorder.application.EatInOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/eat-in-orders")
@RestController
public class EatInOrderController {

    private final OrderService orderService;
    private final EatInOrderService eatInOrderService;

    public EatInOrderController(OrderService orderService, EatInOrderService eatInOrderService) {
        this.orderService = orderService;
        this.eatInOrderService = eatInOrderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody final Order request) {
        final Order response = eatInOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/eat-in-orders/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<Order> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<Order> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.serve(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<Order> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
