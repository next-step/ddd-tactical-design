package kitchenpos.takeoutorders.ui;

import kitchenpos.takeoutorders.application.TakeoutOrderService;
import kitchenpos.takeoutorders.domain.TakeoutOrder;
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

@RequestMapping("/api/orders/takeout")
@RestController
public class TakeoutOrderRestController {
    private final TakeoutOrderService orderService;

    public TakeoutOrderRestController(final TakeoutOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<TakeoutOrder> create(@RequestBody final TakeoutOrder request) {
        final TakeoutOrder response = orderService.create(request);
        return ResponseEntity.created(URI.create("/api/orders/takeout/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<TakeoutOrder> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<TakeoutOrder> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.serve(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<TakeoutOrder> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(orderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<TakeoutOrder>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
