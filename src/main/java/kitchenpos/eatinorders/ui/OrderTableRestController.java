package kitchenpos.eatinorders.ui;

import kitchenpos.eatinorders.application.orderTables.OrderTableService;
import kitchenpos.eatinorders.application.orderTables.dto.OrderTableResponse;
import kitchenpos.eatinorders.domain.ordertables.NumberOfGuests;
import kitchenpos.eatinorders.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.domain.ordertables.OrderTableName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/order-tables")
@RestController
public class OrderTableRestController {
    private final OrderTableService orderTableService;

    public OrderTableRestController(final OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping
    public ResponseEntity<OrderTableResponse> create(@RequestBody final OrderTableName request) {
        final OrderTable orderTable = orderTableService.create(request);
        return ResponseEntity.created(URI.create("/api/order-tables/" + orderTable.getId()))
                .body(OrderTableResponse.from(orderTable));
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<OrderTableResponse> sit(@PathVariable final UUID orderTableId) {
        OrderTable orderTable = orderTableService.sit(orderTableId);
        return ResponseEntity.ok(OrderTableResponse.from(orderTable));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableResponse> changeNumberOfGuests(
            @PathVariable final UUID orderTableId,
            @RequestBody final NumberOfGuests request
    ) {
        OrderTable orderTable = orderTableService.changeNumberOfGuests(orderTableId, request);
        return ResponseEntity.ok(OrderTableResponse.from(orderTable));
    }

    @GetMapping
    public ResponseEntity<List<OrderTableResponse>> findAll() {
        List<OrderTableResponse> responses = orderTableService.findAll().stream()
                .map(OrderTableResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
