package kitchenpos.eatinorder.adapter.in.web;

import kitchenpos.eatinorder.application.service.OrderTableService;
import kitchenpos.eatinorder.domain.EatInOrderTable;
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
    public ResponseEntity<EatInOrderTable> create(@RequestBody final EatInOrderTable request) {
        final EatInOrderTable response = orderTableService.create(request);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<EatInOrderTable> sit(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.sit(orderTableId));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<EatInOrderTable> clear(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.clear(orderTableId));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<EatInOrderTable> changeNumberOfGuests(
            @PathVariable final UUID orderTableId,
            @RequestBody final EatInOrderTable request
    ) {
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(orderTableId, request));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderTable>> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
}
