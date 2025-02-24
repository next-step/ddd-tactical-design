package kitchenpos.order.eatinorder.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.service.EatInOrderService;
import kitchenpos.order.eatinorder.ui.dto.CreateEatInOrderRq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/eatInOrder")
@RestController
public class EatInOrderRestController {
    private final EatInOrderService eatInOrderService;

    public EatInOrderRestController(final EatInOrderService eatInOrderService) {
        this.eatInOrderService = eatInOrderService;
    }

    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody final CreateEatInOrderRq request) {
        final UUID eatInOrderId = eatInOrderService.create(request.createServiceRq());
        return ResponseEntity.created(URI.create("/api/orders/" + eatInOrderId))
                .body(eatInOrderId);
    }

    @PutMapping("/{eatInOrderId}/accept")
    public ResponseEntity<EatInOrder> accept(@PathVariable("eatInOrderId") final UUID eatInOrderId) {
        return ResponseEntity.ok(eatInOrderService.accept(eatInOrderId));
    }

    @PutMapping("/{eatInOrderId}/serve")
    public ResponseEntity<EatInOrder> serve(@PathVariable("eatInOrderId") final UUID eatInOrderId) {
        return ResponseEntity.ok(eatInOrderService.serve(eatInOrderId));
    }

    @PutMapping("/{eatInOrderId}/complete")
    public ResponseEntity<EatInOrder> complete(@PathVariable("eatInOrderId") final UUID eatInOrderId) {
        return ResponseEntity.ok(eatInOrderService.complete(eatInOrderId));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrder>> findAll() {
        return ResponseEntity.ok(eatInOrderService.findAll());
    }
}
