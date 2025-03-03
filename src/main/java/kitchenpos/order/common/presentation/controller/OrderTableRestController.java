package kitchenpos.order.common.presentation.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.common.application.dto.OrderTableRequest;
import kitchenpos.order.common.application.dto.OrderTableRequest.UpdateGuests;
import kitchenpos.order.common.application.dto.OrderTableResponse;
import kitchenpos.order.common.application.facade.OrderFacade;
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

    private final OrderFacade orderFacade;

    public OrderTableRestController(
        final OrderFacade orderFacade
    ) {
        this.orderFacade = orderFacade;
    }

    @PostMapping
    public ResponseEntity<OrderTableResponse.GetOrderTable> create(@RequestBody final OrderTableRequest.Create request) {
        final OrderTableResponse.GetOrderTable response = orderFacade.createOrderTable(request);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.id()))
            .body(response);
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<OrderTableResponse.GetOrderTable> sit(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderFacade.sit(orderTableId));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<OrderTableResponse.GetOrderTable> clear(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderFacade.clear(orderTableId));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableResponse.GetOrderTable> changeNumberOfGuests(
        @PathVariable final UUID orderTableId,
        @RequestBody final OrderTableRequest.UpdateGuests request
    ) {
        return ResponseEntity.ok(orderFacade.changeNumberOfGuests(new UpdateGuests(orderTableId, request.guests())));
    }

    @GetMapping
    public ResponseEntity<List<OrderTableResponse.GetOrderTable>> findAll() {
        return ResponseEntity.ok(orderFacade.findOrderTableAll());
    }
}
