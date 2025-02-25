package kitchenpos.order.eatinorder.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.eatinorder.application.OrderTableService;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.service.dto.CreateOrderTableServiceRq;
import kitchenpos.order.eatinorder.service.dto.OrderTableServiceRs;
import kitchenpos.order.eatinorder.ui.dto.CreateOrderTableRq;
import kitchenpos.order.eatinorder.ui.dto.OrderTableRs;
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
    public ResponseEntity<OrderTableRs> create(@RequestBody final CreateOrderTableRq request) {
        OrderTableServiceRs response = orderTableService.create(
                new CreateOrderTableServiceRq(request.getName()));
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
                .body(new OrderTableRs(response));
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<OrderTableRs> sit(@PathVariable("orderTableId") final UUID orderTableId) {
        OrderTableServiceRs response = orderTableService.sit(orderTableId);
        return ResponseEntity.ok(new OrderTableRs(response));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<OrderTableRs> clear(@PathVariable("orderTableId") final UUID orderTableId) {
        OrderTableServiceRs response = orderTableService.clear(orderTableId);
        return ResponseEntity.ok(new OrderTableRs(response));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableRs> changeNumberOfGuests(
            @PathVariable("orderTableId") final UUID orderTableId,
            @RequestBody final OrderTable request
    ) {
        OrderTableServiceRs response = orderTableService.changeNumberOfGuests(orderTableId, request);
        return ResponseEntity.ok(new OrderTableRs(response));
    }

    @GetMapping
    public ResponseEntity<List<OrderTableRs>> findAll() {
        List<OrderTableServiceRs> response = orderTableService.findAll();
        return ResponseEntity.ok(
                response.stream()
                        .map(OrderTableRs::new)
                        .toList()
        );
    }
}
