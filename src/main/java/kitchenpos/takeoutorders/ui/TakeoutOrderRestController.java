package kitchenpos.takeoutorders.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.takeoutorders.application.TakeoutOrderRequestDto;
import kitchenpos.takeoutorders.application.TakeoutOrderResponseDto;
import kitchenpos.takeoutorders.application.TakeoutOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/orders/takeout")
@RestController
public class TakeoutOrderRestController {
  private final TakeoutOrderService orderService;

  public TakeoutOrderRestController(final TakeoutOrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<TakeoutOrderResponseDto> create(
      @RequestBody final TakeoutOrderRequestDto request) {
    final TakeoutOrderResponseDto response = orderService.create(request);
    return ResponseEntity.created(URI.create("/api/orders/" + response.getOrderId()))
        .body(response);
  }

  @PutMapping("/{orderId}/accept")
  public ResponseEntity<TakeoutOrderResponseDto> accept(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.accept(orderId));
  }

  @PutMapping("/{orderId}/serve")
  public ResponseEntity<TakeoutOrderResponseDto> serve(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.serve(orderId));
  }

  @PutMapping("/{orderId}/complete")
  public ResponseEntity<TakeoutOrderResponseDto> complete(@PathVariable final UUID orderId) {
    return ResponseEntity.ok(orderService.complete(orderId));
  }

  @GetMapping
  public ResponseEntity<List<TakeoutOrderResponseDto>> findAll() {
    return ResponseEntity.ok(orderService.findAll());
  }
}
