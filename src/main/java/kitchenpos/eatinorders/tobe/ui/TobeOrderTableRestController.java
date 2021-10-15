package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.tobe.application.TobeOrderTableService;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequestMapping("/api/order-tables")
@RestController
public class TobeOrderTableRestController {
    private final TobeOrderTableService orderTableService;

    public TobeOrderTableRestController(final TobeOrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping
    public ResponseEntity<OrderTableForm> create(@RequestBody final OrderTableForm request) {
        final OrderTableForm response = orderTableService.create(request);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<OrderTableForm> sit(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.sit(orderTableId));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<OrderTableForm> clear(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.clear(orderTableId));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableForm> changeNumberOfGuests(
        @PathVariable final UUID orderTableId,
        @RequestBody final OrderTableForm request
    ) {
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(orderTableId, request));
    }
/*
    @GetMapping
    public ResponseEntity<List<OrderTable>> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
*/
}
