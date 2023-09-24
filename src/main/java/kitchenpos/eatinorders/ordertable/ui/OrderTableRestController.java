package kitchenpos.eatinorders.ordertable.ui;

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

import kitchenpos.eatinorders.ordertable.application.OrderTableService;
import kitchenpos.eatinorders.ordertable.application.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.ordertable.application.dto.CreateOrderTableRequest;
import kitchenpos.eatinorders.ordertable.application.dto.OrderTableResponse;

@RequestMapping("/api/order-tables")
@RestController
public class OrderTableRestController {
    private final OrderTableService orderTableService;

    public OrderTableRestController(final OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping
    public ResponseEntity<OrderTableResponse> create(@RequestBody final CreateOrderTableRequest request) {
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
        @RequestBody final ChangeNumberOfGuestsRequest request
    ) {
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(orderTableId, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderTableResponse>> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
}
