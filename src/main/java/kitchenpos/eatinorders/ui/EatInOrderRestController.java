package kitchenpos.eatinorders.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.application.EatInOrderService;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.ui.request.EatInOrderCreateRequest;
import kitchenpos.eatinorders.ui.response.EatInOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/eat-in-orders")
@RestController
public class EatInOrderRestController {
    private final EatInOrderService eatInOrderService;

    public EatInOrderRestController(final EatInOrderService eatInOrderService) {
        this.eatInOrderService = eatInOrderService;
    }

    @PostMapping
    public ResponseEntity<EatInOrderResponse> create(@RequestBody final EatInOrderCreateRequest request) {
        final EatInOrderResponse response = eatInOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/eat-in-orders/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{eatInOrderId}/accept")
    public ResponseEntity<EatInOrder> accept(@PathVariable final UUID eatInOrderId) {
        return ResponseEntity.ok(eatInOrderService.accept(eatInOrderId));
    }

    @PutMapping("/{eatInOrderId}/serve")
    public ResponseEntity<EatInOrder> serve(@PathVariable final UUID eatInOrderId) {
        return ResponseEntity.ok(eatInOrderService.serve(eatInOrderId));
    }

    @PutMapping("/{eatInOrderId}/complete")
    public ResponseEntity<EatInOrder> complete(@PathVariable final UUID eatInOrderId) {
        return ResponseEntity.ok(eatInOrderService.complete(eatInOrderId));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrder>> findAll() {
        return ResponseEntity.ok(eatInOrderService.findAll());
    }
}
