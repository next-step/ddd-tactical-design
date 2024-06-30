package kitchenpos.eatinorders.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.application.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/order-tables")
@RestController
public class OrderTableRestController {
  private final EatInOrderTableService orderTableService;

  public OrderTableRestController(final EatInOrderTableService orderTableService) {
    this.orderTableService = orderTableService;
  }

  @PostMapping
  public ResponseEntity<EatInOrderTableResponse> create(
      @RequestBody final EatInOrderTableCreateRequestDto request) {
    final EatInOrderTableResponse response = orderTableService.create(request.getName());
    return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
        .body(response);
  }

  @PutMapping("/{orderTableId}/sit/{numberOfGuests}")
  public ResponseEntity<EatInOrderTableResponse> sit(
      @PathVariable final UUID orderTableId, @PathVariable final int numberOfGuests) {
    return ResponseEntity.ok(orderTableService.sit(orderTableId, numberOfGuests));
  }

  @PutMapping("/{orderTableId}/clear")
  public ResponseEntity<EatInOrderTableResponse> clear(@PathVariable final UUID orderTableId) {
    return ResponseEntity.ok(orderTableService.clear(orderTableId));
  }

  @PutMapping("/{orderTableId}/number-of-guests")
  public ResponseEntity<EatInOrderTableResponse> changeNumberOfGuests(
      @PathVariable final UUID orderTableId,
      @RequestBody final EatInOrderTableChangeNumberGuestsRequestDto request) {
    return ResponseEntity.ok(
        orderTableService.changeNumberOfGuests(orderTableId, request.getNumberOfGuests()));
  }

  @GetMapping
  public ResponseEntity<List<EatInOrderTableResponse>> findAll() {
    return ResponseEntity.ok(orderTableService.findAll());
  }
}
