package kitchenpos.order.tobe.eatinorder.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.tobe.eatinorder.application.OrderTableService;
import kitchenpos.order.tobe.eatinorder.application.dto.ChangeNumberOfGuestRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.CreateOrderTableRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.DetailOrderTableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/order-tables")
@RestController
public class OrderTableRestController {

    private final OrderTableService orderTableService;

    public OrderTableRestController(final OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping
    public ResponseEntity<DetailOrderTableResponse> create(@RequestBody final CreateOrderTableRequest request) {
        final var response = orderTableService.create(request);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<DetailOrderTableResponse> sit(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.sit(orderTableId));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<DetailOrderTableResponse> clear(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.clear(orderTableId));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<DetailOrderTableResponse> changeNumberOfGuests(
        @PathVariable final UUID orderTableId,
        @RequestBody final ChangeNumberOfGuestRequest request
    ) {
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(orderTableId, request));
    }

    @GetMapping
    public ResponseEntity<List<DetailOrderTableResponse>> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
}
