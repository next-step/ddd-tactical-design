package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.tobe.application.EatInOrderService;
import kitchenpos.eatinorders.tobe.application.dto.request.OrderCreateRequest;
import kitchenpos.eatinorders.tobe.application.dto.response.OrderResponse;
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

@RequestMapping("/api/orders")
@RestController
public class OrderRestController {
    private final EatInOrderService eatInOrderService;

    public OrderRestController(final EatInOrderService eatInOrderService) {
        this.eatInOrderService = eatInOrderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody final OrderCreateRequest request) {
        final OrderResponse response = eatInOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<OrderResponse> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<OrderResponse> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.serve(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<OrderResponse> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(eatInOrderService.findAll());
    }
}
