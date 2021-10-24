package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.tobe.application.TobeOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/tobe/api/orders")
@RestController
public class TobeOrderRestController {
    private final TobeOrderService orderService;

    public TobeOrderRestController(final TobeOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderForm> create(@RequestBody final OrderForm request) {
        final OrderForm response = orderService.create(request);
        return ResponseEntity.created(URI.create("/tobe/api/orders/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<OrderForm> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<OrderForm> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.serve(orderId));
    }

    @PutMapping("/{orderId}/start-delivery")
    public ResponseEntity<OrderForm> startDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.startDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete-delivery")
    public ResponseEntity<OrderForm> completeDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.completeDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<OrderForm> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderForm>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
