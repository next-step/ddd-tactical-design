package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.tobe.application.OrderTableService;
import kitchenpos.eatinorders.tobe.ui.dto.OrderTableRequest;
import kitchenpos.eatinorders.tobe.ui.dto.OrderTableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/order-tables")
@RestController
public class OrderTableRestController {
    private final OrderTableService orderTableService;

    public OrderTableRestController(final OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping
    public ResponseEntity<OrderTableResponse> create(@RequestBody final OrderTableRequest request) {
        final OrderTableResponse response = orderTableService.create(request);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<OrderTableResponse> sit(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.sit(orderTableId));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<OrderTableResponse> clear(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.clear(orderTableId));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableResponse> changeNumberOfGuests(
            @PathVariable final UUID orderTableId,
            @RequestBody final OrderTableRequest request
    ) {
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(orderTableId, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderTableResponse>> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
}
