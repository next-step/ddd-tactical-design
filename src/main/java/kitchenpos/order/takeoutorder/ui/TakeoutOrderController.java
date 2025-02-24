package kitchenpos.order.takeoutorder.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.common.application.OrderService;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.takeoutorder.application.TakeoutOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/takeout-orders")
@RestController
public class TakeoutOrderController {

    private final OrderService orderService;
    private final TakeoutOrderService takeoutOrderService;

    public TakeoutOrderController(OrderService orderService, TakeoutOrderService takeoutOrderService) {
        this.orderService = orderService;
        this.takeoutOrderService = takeoutOrderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody final Order request) {
        final Order response = takeoutOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/takeout-orders/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<Order> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(takeoutOrderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<Order> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(takeoutOrderService.serve(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<Order> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(takeoutOrderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
