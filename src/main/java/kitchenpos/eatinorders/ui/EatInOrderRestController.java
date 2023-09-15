package kitchenpos.eatinorders.ui;

import kitchenpos.eatinorders.application.EatInOrderService;
import kitchenpos.eatinorders.ui.request.EatInOrderCreateRequest;
import kitchenpos.eatinorders.ui.response.EatInOrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/orders")
@RestController
public class EatInOrderRestController {
    private final EatInOrderService eatInOrderService;

    public EatInOrderRestController(final EatInOrderService eatInOrderService) {
        this.eatInOrderService = eatInOrderService;
    }

    @PostMapping
    public ResponseEntity<EatInOrderResponse> create(@Validated @RequestBody final EatInOrderCreateRequest request) {
        final EatInOrderResponse response = EatInOrderResponse.of(eatInOrderService.create(request));
        return ResponseEntity.created(URI.create("/api/orders/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<EatInOrderResponse> accept(@PathVariable final UUID orderId) {
        EatInOrderResponse response = EatInOrderResponse.of(eatInOrderService.accept(orderId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<EatInOrderResponse> serve(@PathVariable final UUID orderId) {
        EatInOrderResponse response = EatInOrderResponse.of(eatInOrderService.serve(orderId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/complete")
    public ResponseEntity<EatInOrderResponse> complete(@PathVariable final UUID orderId) {
        EatInOrderResponse response = EatInOrderResponse.of(eatInOrderService.complete(orderId));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderResponse>> findAll() {
        List<EatInOrderResponse> repsonse = eatInOrderService.findAll()
                .stream()
                .map(EatInOrderResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(repsonse);
    }
}
