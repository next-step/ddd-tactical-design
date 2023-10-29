package kitchenpos.eatinorders.ui;

import kitchenpos.eatinorders.application.OrderService;
import kitchenpos.eatinorders.application.dto.EatInOrderRequest;
import kitchenpos.eatinorders.application.dto.EatInOrderResponse;
import kitchenpos.eatinorders.application.dto.OrderStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/orders")
@RestController
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<EatInOrderResponse> create(@RequestBody final EatInOrderRequest request) {
        final EatInOrderResponse response = orderService.create(request);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<OrderStatusResponse> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<OrderStatusResponse> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.serve(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<OrderStatusResponse> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderResponse>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
