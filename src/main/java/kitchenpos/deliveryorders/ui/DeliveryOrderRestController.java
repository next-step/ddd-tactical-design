package kitchenpos.deliveryorders.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.deliveryorders.application.DeliveryOrderRequestDto;
import kitchenpos.deliveryorders.application.DeliveryOrderResponseDto;
import kitchenpos.deliveryorders.application.DeliveryOrderService;
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
public class DeliveryOrderRestController {
  private final DeliveryOrderService orderService;

  public DeliveryOrderRestController(final DeliveryOrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<DeliveryOrderResponseDto> create(
      @RequestBody final DeliveryOrderRequestDto request) {
    final DeliveryOrderResponseDto response = orderService.create(request);
    return ResponseEntity.created(URI.create("/api/orders/" + response.getOrderId()))
        .body(response);
  }

  @PutMapping("/{orderId}/accept")
  public ResponseEntity<DeliveryOrderResponseDto> accept(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.accept(orderId));
  }

  @PutMapping("/{orderId}/serve")
  public ResponseEntity<DeliveryOrderResponseDto> serve(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.serve(orderId));
  }

  @PutMapping("/{orderId}/start-delivery")
  public ResponseEntity<DeliveryOrderResponseDto> startDelivery(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.startDelivery(orderId));
  }

  @PutMapping("/{orderId}/complete-delivery")
  public ResponseEntity<DeliveryOrderResponseDto> completeDelivery(
      @PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.completeDelivery(orderId));
  }

  @PutMapping("/{orderId}/complete")
  public ResponseEntity<DeliveryOrderResponseDto> complete(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.complete(orderId));
  }

  @GetMapping
  public ResponseEntity<List<DeliveryOrderResponseDto>> findAll() {
    return ResponseEntity.ok(orderService.findAll());
  }
}
