package kitchenpos.eatinorders.ui;

import kitchenpos.eatinorders.application.EatInOrderService;
import kitchenpos.eatinorders.dto.EatInOrderRegisterRequest;
import kitchenpos.eatinorders.dto.EatInOrderResponse;
import kitchenpos.eatinorders.dto.OrderStatusChangeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/api/orders")
@RestController
public class EatInOrderRestController {
    private final EatInOrderService orderService;

    public EatInOrderRestController(final EatInOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody final EatInOrderRegisterRequest request) {
        final EatInOrderResponse response = orderService.create(request);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<?> accept(@Validated @PathVariable final OrderStatusChangeRequest request) {
        return ResponseEntity.ok(orderService.accept(request));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<?> serve(@Validated @PathVariable final OrderStatusChangeRequest request) {
        return ResponseEntity.ok(orderService.serve(request));
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<?> complete(@Validated @PathVariable final OrderStatusChangeRequest request) {
        return ResponseEntity.ok(orderService.complete(request));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
}
