package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.tobe.application.OrderService;
import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.dto.CreateOrderRequest;
import kitchenpos.eatinorders.tobe.dto.OrderCompletedResponse;
import kitchenpos.eatinorders.tobe.dto.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/tobe/orders")
@RestController("TobeOrderRestController")
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody final CreateOrderRequest request) {
        final OrderResponse response = orderService.create(request);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<OrderResponse> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<OrderResponse> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.serve(orderId));
    }

    @PutMapping("/{orderId}/start-delivery")
    public ResponseEntity<OrderResponse> startDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.startDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete-delivery")
    public ResponseEntity<OrderResponse> completeDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.completeDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<OrderResponse> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.complete(orderId));
    }

    @GetMapping("/order-tables/{orderTableId}/completed")
    public ResponseEntity<OrderCompletedResponse> isCompleted(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderService.isCompleted(orderTableId));
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
