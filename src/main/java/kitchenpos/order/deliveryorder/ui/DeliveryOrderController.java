package kitchenpos.order.deliveryorder.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.common.application.OrderService;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.deliveryorder.application.DeliveryOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/delivery-orders")
@RestController
public class DeliveryOrderController {

    private final OrderService orderService;
    private final DeliveryOrderService deliveryOrderService;

    public DeliveryOrderController(OrderService orderService, DeliveryOrderService deliveryOrderService) {
        this.orderService = orderService;
        this.deliveryOrderService = deliveryOrderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody final Order request) {
        final Order response = deliveryOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/delivery-orders/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<Order> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(deliveryOrderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<Order> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(deliveryOrderService.serve(orderId));
    }

    @PutMapping("/{orderId}/start-delivery")
    public ResponseEntity<Order> startDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(deliveryOrderService.startDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete-delivery")
    public ResponseEntity<Order> completeDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(deliveryOrderService.completeDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<Order> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(deliveryOrderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
