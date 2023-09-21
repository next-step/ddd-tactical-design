package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.tobe.application.EatInOrderService;
import kitchenpos.eatinorders.tobe.application.dto.EatInOrderRequest;
import kitchenpos.eatinorders.tobe.domain.EatInOrder;
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
    public ResponseEntity<EatInOrder> create(@RequestBody final EatInOrderRequest request) {
        final EatInOrder response = eatInOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<EatInOrder> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<EatInOrder> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.serve(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<EatInOrder> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(eatInOrderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrder>> findAll() {
        return ResponseEntity.ok(eatInOrderService.findAll());
    }
}
