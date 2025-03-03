package kitchenpos.legacy.order.deliveryorder.representation;

import kitchenpos.legacy.order.deliveryorder.application.DeliveryOrderService;
import kitchenpos.legacy.order.deliveryorder.domain.model.DeliveryOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/delivery-orders")
@RestController
public class DeliveryOrderRestController {
    private final DeliveryOrderService orderService;

    public DeliveryOrderRestController(final DeliveryOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<DeliveryOrder> create(@RequestBody final DeliveryOrder request) {
        final DeliveryOrder response = orderService.create(request);
        return ResponseEntity.created(URI.create("/api/delivery-orders/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<DeliveryOrder> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<DeliveryOrder> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.serve(orderId));
    }

    @PutMapping("/{orderId}/start-delivery")
    public ResponseEntity<DeliveryOrder> startDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.startDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete-delivery")
    public ResponseEntity<DeliveryOrder> completeDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.completeDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<DeliveryOrder> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<DeliveryOrder>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
