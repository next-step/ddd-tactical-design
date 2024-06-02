package kitchenpos.eatinorders.ui;

import kitchenpos.eatinorders.application.OrderTableService;
import kitchenpos.eatinorders.dto.OrderTableResponse;
import kitchenpos.eatinorders.todo.domain.ordertable.NumberOfGuests;
import kitchenpos.eatinorders.todo.domain.ordertable.OrderTableName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<OrderTableResponse> create(@RequestBody final OrderTableName request) {
        final OrderTableResponse response = orderTableService.create(request);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.id()))
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
        @RequestBody final NumberOfGuests request
    ) {
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(orderTableId, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderTableResponse>> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
}
