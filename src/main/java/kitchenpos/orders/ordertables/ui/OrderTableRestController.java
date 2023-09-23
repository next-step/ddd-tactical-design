package kitchenpos.orders.ordertables.ui;

import kitchenpos.orders.ordertables.application.OrderTableService;
import kitchenpos.orders.ordertables.application.dto.OrderTableResponse;
import kitchenpos.orders.ordertables.domain.OrderTableId;
import kitchenpos.orders.ordertables.ui.dto.OrderTableRestMapper;
import kitchenpos.orders.ordertables.ui.dto.request.ChangeNumberOfGuestRestRequest;
import kitchenpos.orders.ordertables.ui.dto.request.OrderTableRestRequest;
import kitchenpos.orders.ordertables.ui.dto.response.OrderTableRestResponse;
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
    public ResponseEntity<OrderTableRestResponse> create(@RequestBody final OrderTableRestRequest request) {
        final OrderTableResponse response = orderTableService.create(OrderTableRestMapper.toDto(request));
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
                .body(OrderTableRestMapper.toRestDto(response));
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<OrderTableRestResponse> sit(@PathVariable final UUID orderTableId) {
        OrderTableResponse response = orderTableService.sit(new OrderTableId(orderTableId));
        return ResponseEntity.ok(OrderTableRestMapper.toRestDto(response));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<OrderTableRestResponse> clear(@PathVariable final UUID orderTableId) {
        OrderTableResponse response = orderTableService.clear(new OrderTableId(orderTableId));
        return ResponseEntity.ok(OrderTableRestMapper.toRestDto(response));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableRestResponse> changeNumberOfGuests(
            @PathVariable final UUID orderTableId,
            @RequestBody final ChangeNumberOfGuestRestRequest request
    ) {
        OrderTableId targetOrderTableId = new OrderTableId(orderTableId);
        OrderTableResponse response = orderTableService.changeNumberOfGuests(targetOrderTableId, request.getNumberOfGuest());
        return ResponseEntity.ok(OrderTableRestMapper.toRestDto(response));
    }

    @GetMapping
    public ResponseEntity<List<OrderTableRestResponse>> findAll() {
        List<OrderTableResponse> responses = orderTableService.findAll();
        return ResponseEntity.ok(OrderTableRestMapper.toRestDtos(responses));
    }
}
