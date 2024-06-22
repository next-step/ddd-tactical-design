package kitchenpos.orders.store.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.orders.store.application.StoreOrderService;
import kitchenpos.orders.store.application.dto.StoreOrderCreateRequest;
import kitchenpos.orders.store.domain.tobe.StoreOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/store-orders")
@RestController
public class StoreOrderRestController {

    private final StoreOrderService storeOrderService;

    public StoreOrderRestController(final StoreOrderService storeOrderService) {
        this.storeOrderService = storeOrderService;
    }

    @PostMapping
    public ResponseEntity<StoreOrder> create(@RequestBody final StoreOrderCreateRequest request) {
        StoreOrder response = storeOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/store-orders/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<StoreOrder> accept(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(storeOrderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<StoreOrder> serve(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(storeOrderService.serve(orderId));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<StoreOrder> complete(@PathVariable final UUID orderId) {
        return ResponseEntity.ok(storeOrderService.complete(orderId));
    }

    @GetMapping
    public ResponseEntity<List<StoreOrder>> findAll() {
        return ResponseEntity.ok(storeOrderService.findAll());
    }
}
