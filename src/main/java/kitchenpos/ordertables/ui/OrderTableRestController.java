package kitchenpos.ordertables.ui;

import kitchenpos.ordertables.application.OrderTableService;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableId;
import kitchenpos.ordertables.dto.ChangeNumberOfGuestRequest;
import kitchenpos.ordertables.dto.OrderTableRequest;
import kitchenpos.ordertables.dto.OrderTableResponse;
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
        final OrderTable response = orderTableService.create(request);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getIdValue()))
                .body(OrderTableResponse.fromEntity(response));
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<OrderTableResponse> sit(@PathVariable final UUID orderTableId) {
        OrderTable response = orderTableService.sit(new OrderTableId(orderTableId));
        return ResponseEntity.ok(OrderTableResponse.fromEntity(response));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<OrderTableResponse> clear(@PathVariable final UUID orderTableId) {
        OrderTable response = orderTableService.clear(new OrderTableId(orderTableId));
        return ResponseEntity.ok(OrderTableResponse.fromEntity(response));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableResponse> changeNumberOfGuests(
            @PathVariable final UUID orderTableId,
            @RequestBody final ChangeNumberOfGuestRequest request
    ) {
        OrderTableId targetOrderTableId = new OrderTableId(orderTableId);
        OrderTable response = orderTableService.changeNumberOfGuests(targetOrderTableId, request.getNumberOfGuest());
        return ResponseEntity.ok(OrderTableResponse.fromEntity(response));
    }

    @GetMapping
    public ResponseEntity<List<OrderTableResponse>> findAll() {
        List<OrderTable> orderTables = orderTableService.findAll();
        return ResponseEntity.ok(OrderTableResponse.fromEntities(orderTables));
    }
}
