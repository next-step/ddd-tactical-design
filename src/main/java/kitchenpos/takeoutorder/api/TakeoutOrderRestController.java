package kitchenpos.takeoutorder.api;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.takeoutorder.application.TakeoutOrderService;
import kitchenpos.takeoutorder.domain.TakeoutOrder;
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
public class TakeoutOrderRestController {
    private final TakeoutOrderService takeoutOrderService;

    public TakeoutOrderRestController(final TakeoutOrderService takeoutOrderService) {
        this.takeoutOrderService = takeoutOrderService;
    }

    @PostMapping
    public ResponseEntity<TakeoutOrder> create(@RequestBody final TakeoutOrder request) {
        final TakeoutOrder response = takeoutOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<TakeoutOrder> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(takeoutOrderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<TakeoutOrder> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(takeoutOrderService.serve(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<TakeoutOrder> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(takeoutOrderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<TakeoutOrder>> findAll() {
        return ResponseEntity.ok(takeoutOrderService.findAll());
    }
}
