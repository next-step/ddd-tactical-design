package kitchenpos.eatinorders.ui;

import kitchenpos.eatinorders.application.OrderTableService;
import kitchenpos.eatinorders.application.dto.*;
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
    public ResponseEntity<Boolean> sit(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.sit(orderTableId));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<OrderTableClearResponse> clear(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.clear(orderTableId));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableChangeNumberOfGuestsResponse> changeNumberOfGuests(
            @PathVariable final UUID orderTableId,
            @RequestBody final OrderTableChangeNumberOfGuestsRequest request
    ) {
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(orderTableId, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderTableResponse>> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
}
