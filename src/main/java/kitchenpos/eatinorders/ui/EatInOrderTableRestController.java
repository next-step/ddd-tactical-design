package kitchenpos.eatinorders.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.application.EatInOrderTableService;
import kitchenpos.eatinorders.ui.request.EatInOrderTableChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.ui.request.EatInOrderTableCreateRequest;
import kitchenpos.eatinorders.ui.response.EatInOrderTableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/eat-in-order-tables")
@RestController
public class EatInOrderTableRestController {
    private final EatInOrderTableService eatInOrderTableService;

    public EatInOrderTableRestController(EatInOrderTableService eatInOrderTableService) {
        this.eatInOrderTableService = eatInOrderTableService;
    }

    @PostMapping
    public ResponseEntity<EatInOrderTableResponse> create(@RequestBody EatInOrderTableCreateRequest request) {
        final EatInOrderTableResponse response = eatInOrderTableService.create(request);
        return ResponseEntity.created(URI.create("/api/eat-in-order-tables/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{eatInOrderTableId}/sit")
    public ResponseEntity<EatInOrderTableResponse> sit(@PathVariable UUID eatInOrderTableId) {
        return ResponseEntity.ok(eatInOrderTableService.sit(eatInOrderTableId));
    }

    @PutMapping("/{eatInOrderTableId}/clear")
    public ResponseEntity<EatInOrderTableResponse> clear(@PathVariable UUID eatInOrderTableId) {
        return ResponseEntity.ok(eatInOrderTableService.clear(eatInOrderTableId));
    }

    @PutMapping("/{eatInOrderTableId}/number-of-guests")
    public ResponseEntity<EatInOrderTableResponse> changeNumberOfGuests(
        @PathVariable UUID eatInOrderTableId,
        @RequestBody EatInOrderTableChangeNumberOfGuestsRequest request
    ) {
        return ResponseEntity.ok(eatInOrderTableService.changeNumberOfGuests(eatInOrderTableId, request));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderTableResponse>> findAll() {
        return ResponseEntity.ok(eatInOrderTableService.findAll());
    }
}
