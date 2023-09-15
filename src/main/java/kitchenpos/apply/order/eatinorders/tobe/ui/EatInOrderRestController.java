package kitchenpos.apply.order.eatinorders.tobe.ui;

import kitchenpos.apply.order.eatinorders.tobe.ui.dto.EatInOrderResponse;
import kitchenpos.apply.order.eatinorders.tobe.application.EatInOrderService;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.EatInOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/eat-in-orders")
@RestController
public class EatInOrderRestController {

    private final EatInOrderService eatInOrderService;

    public EatInOrderRestController(final EatInOrderService eatInOrderService) {
        this.eatInOrderService = eatInOrderService;
    }

    @PostMapping
    public ResponseEntity<EatInOrderResponse> create(@RequestBody final EatInOrderRequest request) {
        final EatInOrderResponse response = eatInOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<EatInOrderResponse> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<EatInOrderResponse> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.serve(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<EatInOrderResponse> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderResponse>> findAll() {
        return ResponseEntity.ok(eatInOrderService.findAll());
    }
}
