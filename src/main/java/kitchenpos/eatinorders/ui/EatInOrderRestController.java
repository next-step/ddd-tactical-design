package kitchenpos.eatinorders.ui;

import kitchenpos.eatinorders.application.orders.EatInOrderService;
import kitchenpos.eatinorders.application.orders.dto.EatInOrderCreateRequest;
import kitchenpos.eatinorders.application.orders.dto.EatInOrderResponse;
import kitchenpos.eatinorders.domain.orders.EatInOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/eat-in-orders")
@RestController
public class EatInOrderRestController {
    private final EatInOrderService eatInOrderService;

    public EatInOrderRestController(EatInOrderService eatInOrderService) {
        this.eatInOrderService = eatInOrderService;
    }

    @PostMapping
    public ResponseEntity<EatInOrderResponse> create(@RequestBody final EatInOrderCreateRequest request) {
        final EatInOrder order = eatInOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/orders/" + order.getId()))
                .body(EatInOrderResponse.from(order));
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<EatInOrderResponse> accept(@PathVariable final UUID orderId) {
        EatInOrder order = eatInOrderService.accept(orderId);
        return ResponseEntity.ok(EatInOrderResponse.from(order));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<EatInOrderResponse> serve(@PathVariable final UUID orderId) {
        EatInOrder order = eatInOrderService.serve(orderId);
        return ResponseEntity.ok(EatInOrderResponse.from(order));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<EatInOrderResponse> complete(@PathVariable final UUID orderId) {
        EatInOrder order = eatInOrderService.complete(orderId);
        return ResponseEntity.ok(EatInOrderResponse.from(order));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderResponse>> findAll() {
        List<EatInOrderResponse> responses = eatInOrderService.findAll().stream()
                .map(EatInOrderResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
