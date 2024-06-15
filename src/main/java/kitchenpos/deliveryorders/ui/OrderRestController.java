package kitchenpos.deliveryorders.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.application.EatInOrderService;
import kitchenpos.eatinorders.domain.EatInOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/orders")
@RestController
public class OrderRestController {
  private final EatInOrderService orderService;

  public OrderRestController(final EatInOrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<EatInOrder> create(@RequestBody final EatInOrder request) {
    final EatInOrder response = orderService.create(request);
    return ResponseEntity.created(URI.create("/api/orders/" + response.getId())).body(response);
  }

  @PutMapping("/{orderId}/accept")
  public ResponseEntity<EatInOrder> accept(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.accept(orderId));
  }

  @PutMapping("/{orderId}/serve")
  public ResponseEntity<EatInOrder> serve(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.serve(orderId));
  }

  @PutMapping("/{orderId}/start-delivery")
  public ResponseEntity<EatInOrder> startDelivery(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.startDelivery(orderId));
  }

  @PutMapping("/{orderId}/complete-delivery")
  public ResponseEntity<EatInOrder> completeDelivery(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.completeDelivery(orderId));
  }

  @PutMapping("/{orderId}/complete")
  public ResponseEntity<EatInOrder> complete(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.complete(orderId));
  }

  @GetMapping
  public ResponseEntity<List<EatInOrder>> findAll() {
    return ResponseEntity.ok(orderService.findAll());
  }
}
