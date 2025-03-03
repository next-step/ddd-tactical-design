package kitchenpos.order.common.presentation.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.common.application.dto.OrderRequest;
import kitchenpos.order.common.application.dto.OrderResponse;
import kitchenpos.order.common.application.facade.OrderFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/orders")
@RestController
public class OrderRestController {

    private final OrderFacade orderFacade;

    public OrderRestController(final OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @PostMapping
    public ResponseEntity<OrderResponse.GetOrder> create(
        @RequestBody @Valid final OrderRequest.Create request
    ) {
        final OrderResponse.GetOrder response = orderFacade.create(request);
        return ResponseEntity.created(URI.create("/api/orders/" + response.id()))
            .body(response);
    }
    @PutMapping("/{orderId}/accept")
    public ResponseEntity<OrderResponse.GetOrder> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderFacade.accept(orderId));
    }
    @PutMapping("/{orderId}/serve")
    public ResponseEntity<OrderResponse.GetOrder> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderFacade.serve(orderId));
    }

    @PutMapping("/{orderId}/start-delivery")
    public ResponseEntity<OrderResponse.GetOrder> startDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderFacade.startDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete-delivery")
    public ResponseEntity<OrderResponse.GetOrder> completeDelivery(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderFacade.completeDelivery(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<OrderResponse.GetOrder> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderFacade.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse.GetOrder>> findAll() {
        return ResponseEntity.ok(orderFacade.findAll());
    }
}
