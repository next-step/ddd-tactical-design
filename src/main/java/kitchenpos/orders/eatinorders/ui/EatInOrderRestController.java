package kitchenpos.orders.eatinorders.ui;

import kitchenpos.orders.eatinorders.application.EatInOrderService;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderRequest;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderResponse;
import kitchenpos.orders.eatinorders.domain.EatInOrderId;
import kitchenpos.orders.eatinorders.ui.dto.EatInOrderRestMapper;
import kitchenpos.orders.eatinorders.ui.dto.response.EatInOrderRestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/eat-in-orders")
@RestController
public class EatInOrderRestController {
    private final EatInOrderService orderService;

    public EatInOrderRestController(final EatInOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<EatInOrderRestResponse> create(@RequestBody final EatInOrderRequest request) {
        final EatInOrderResponse response = orderService.create(request);
        return ResponseEntity.created(URI.create("/api/eat-in-orders/" + response.getId()))
                .body(EatInOrderRestMapper.toRestDto(response));
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<EatInOrderRestResponse> accept(@PathVariable final UUID orderId) {
        EatInOrderResponse response = orderService.accept(new EatInOrderId(orderId));
        return ResponseEntity.ok(EatInOrderRestMapper.toRestDto(response));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<EatInOrderRestResponse> serve(@PathVariable final UUID orderId) {
        EatInOrderResponse response = orderService.serve(new EatInOrderId(orderId));
        return ResponseEntity.ok(EatInOrderRestMapper.toRestDto(response));
    }


    @PutMapping("/{orderId}/complete")
    public ResponseEntity<EatInOrderRestResponse> complete(@PathVariable final UUID orderId) {
        EatInOrderResponse response = orderService.complete(new EatInOrderId(orderId));
        return ResponseEntity.ok(EatInOrderRestMapper.toRestDto(response));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderRestResponse>> findAll() {
        List<EatInOrderResponse> responses = orderService.findAll();
        return ResponseEntity.ok(EatInOrderRestMapper.toRestDtos(responses));
    }
}
