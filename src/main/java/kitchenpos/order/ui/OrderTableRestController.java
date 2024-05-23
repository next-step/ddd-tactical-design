package kitchenpos.order.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kitchenpos.order.application.OrderTableService;
import kitchenpos.order.domain.OrderTable;

@RequestMapping("/api/order-tables")
@RestController
public class OrderTableRestController {
    private final OrderTableService orderTableService;

    public OrderTableRestController(final OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping
    public ResponseEntity<OrderTable> create(@RequestBody final String name) {
        final OrderTable response = orderTableService.create(name);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<OrderTable> sit(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.sit(orderTableId));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<OrderTable> clear(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.clear(orderTableId));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTable> changeNumberOfGuests(
        @PathVariable final UUID orderTableId,
        @RequestBody final int numberOfGuests
    ) {
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(orderTableId, numberOfGuests));
    }

    @GetMapping
    public ResponseEntity<List<OrderTable>> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
}
