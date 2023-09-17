package kitchenpos.eatinorders.ui;

import kitchenpos.eatinorders.application.EatInOrderService;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderId;
import kitchenpos.eatinorders.dto.EatInOrderRequest;
import kitchenpos.eatinorders.dto.EatInOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/eat-in-orders")
@RestController
public class OrderRestController {
    private final EatInOrderService orderService;

    public OrderRestController(final EatInOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<EatInOrderResponse> create(@RequestBody final EatInOrderRequest request) {
        final EatInOrderResponse response = orderService.create(request);
        return ResponseEntity.created(URI.create("/api/eat-in-orders/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<EatInOrderResponse> accept(@PathVariable final UUID orderId) {
        EatInOrderResponse response = orderService.accept(new EatInOrderId(orderId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<EatInOrderResponse> serve(@PathVariable final UUID orderId) {
        EatInOrderResponse response = orderService.serve(new EatInOrderId(orderId));
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{orderId}/complete")
    public ResponseEntity<EatInOrderResponse> complete(@PathVariable final UUID orderId) {
        EatInOrderResponse response = orderService.complete(new EatInOrderId(orderId));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderResponse>> findAll() {
        List<EatInOrderResponse> responses = orderService.findAll();
        return ResponseEntity.ok(responses);
    }
}
