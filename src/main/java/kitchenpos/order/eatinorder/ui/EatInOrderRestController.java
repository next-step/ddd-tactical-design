package kitchenpos.order.eatinorder.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.eatinorder.service.EatInOrderService;
import kitchenpos.order.eatinorder.service.dto.EatInOrderServiceRs;
import kitchenpos.order.eatinorder.ui.dto.CreateEatInOrderRq;
import kitchenpos.order.eatinorder.ui.dto.EatInOrderRs;
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
    public ResponseEntity<EatInOrderRs> accept(@PathVariable("eatInOrderId") final UUID eatInOrderId) {
        EatInOrderServiceRs response = eatInOrderService.accept(eatInOrderId);
        return ResponseEntity.ok(new EatInOrderRs(response));
    }

    @PutMapping("/{eatInOrderId}/serve")
    public ResponseEntity<EatInOrderRs> serve(@PathVariable("eatInOrderId") final UUID eatInOrderId) {
        EatInOrderServiceRs response = eatInOrderService.serve(eatInOrderId);
        return ResponseEntity.ok(new EatInOrderRs(response));
    }

    @PutMapping("/{eatInOrderId}/complete")
    public ResponseEntity<EatInOrderRs> complete(@PathVariable("eatInOrderId") final UUID eatInOrderId) {
        EatInOrderServiceRs response = eatInOrderService.complete(eatInOrderId);
        return ResponseEntity.ok(new EatInOrderRs(response));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderRs>> findAll() {
        List<EatInOrderServiceRs> response = eatInOrderService.findAll();
        return ResponseEntity.ok(
                response.stream()
                        .map(EatInOrderRs::new)
                        .toList()
        );
    }
}
