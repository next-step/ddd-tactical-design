package kitchenpos.eatinorders.presentation.tobe;

import kitchenpos.eatinorders.application.tobe.OrderTableService;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableId;
import kitchenpos.eatinorders.presentation.dto.OrderTableChangeNumberOfGuestsResponse;
import kitchenpos.eatinorders.presentation.dto.OrderTableClearResponse;
import kitchenpos.eatinorders.presentation.dto.OrderTableCreateResponse;
import kitchenpos.eatinorders.presentation.dto.OrderTableSitResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/order-tables")
@RestController
public class OrderTableRestController {
    private final OrderTableService orderTableService;

    public OrderTableRestController(final OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping
    public ResponseEntity<OrderTableCreateResponse> create(@RequestBody final String taleName) {
        final OrderTableCreateResponse response = orderTableService.create(taleName);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<OrderTableSitResponse> sit(@PathVariable final OrderTableId orderTableId) {
        return ResponseEntity.ok(orderTableService.sit(orderTableId));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<OrderTableClearResponse> clear(@PathVariable final OrderTableId orderTableId) {
        return ResponseEntity.ok(orderTableService.clear(orderTableId));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableChangeNumberOfGuestsResponse> changeNumberOfGuests(
            @PathVariable final OrderTableId orderTableId,
            @RequestBody final int numberOfGuests
    ) {
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(orderTableId, numberOfGuests));
    }

    @GetMapping
    public ResponseEntity<List<OrderTable>> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
}
