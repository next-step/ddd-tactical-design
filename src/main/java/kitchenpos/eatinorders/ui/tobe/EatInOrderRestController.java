package kitchenpos.eatinorders.ui.tobe;

import kitchenpos.eatinorders.application.tobe.EatInOrderService;
import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;
import kitchenpos.eatinorders.ui.dto.EatInOrderAcceptResponse;
import kitchenpos.eatinorders.ui.dto.EatInOrderCreateRequest;
import kitchenpos.eatinorders.ui.dto.EatInOrderCreateResponse;
import kitchenpos.eatinorders.ui.dto.EatInOrderServedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequestMapping("/api/eatinorders")
@RestController
public class EatInOrderRestController {

    private final EatInOrderService eatInOrderService;

    public EatInOrderRestController(final EatInOrderService eatInOrderService) {
        this.eatInOrderService = eatInOrderService;
    }

    @PostMapping
    public ResponseEntity<EatInOrderCreateResponse> create(@RequestBody final EatInOrderCreateRequest request) {
        final EatInOrderCreateResponse response = eatInOrderService.create(request);
        return ResponseEntity.created(URI.create("/api/orders/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<EatInOrderAcceptResponse> accept(@PathVariable final OrderId orderId) {
        return ResponseEntity.ok(eatInOrderService.accept(orderId));
    }

    @PutMapping("/{orderId}/serve")
    public ResponseEntity<EatInOrderServedResponse> serve(@PathVariable final OrderId orderId) {
        return ResponseEntity.ok(eatInOrderService.serve(orderId));
    }
}
