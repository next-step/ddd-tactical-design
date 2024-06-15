package kitchenpos.eatinorders.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.application.EatInOrderRequestDto;
import kitchenpos.eatinorders.application.EatInOrderResponseDto;
import kitchenpos.eatinorders.application.EatInOrderService;
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
  public ResponseEntity<EatInOrderResponseDto> create(
      @RequestBody final EatInOrderRequestDto request) {
    final EatInOrderResponseDto response = orderService.create(request);
    return ResponseEntity.created(URI.create("/api/orders/" + response.getOrderId()))
        .body(response);
  }

  @PutMapping("/{orderId}/accept")
  public ResponseEntity<EatInOrderResponseDto> accept(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.accept(orderId));
  }

  @PutMapping("/{orderId}/serve")
  public ResponseEntity<EatInOrderResponseDto> serve(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.serve(orderId));
  }

  @PutMapping("/{orderId}/complete")
  public ResponseEntity<EatInOrderResponseDto> complete(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.complete(orderId));
  }

  @GetMapping
  public ResponseEntity<List<EatInOrderResponseDto>> findAll() {
    return ResponseEntity.ok(orderService.findAll());
  }
}
