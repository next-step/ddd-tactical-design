package kitchenpos.eatinordertables.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinordertables.application.EatInOrderTableCommandService;
import kitchenpos.eatinordertables.application.EatInOrderTableRepresentationService;
import kitchenpos.eatinordertables.ui.request.EatInOrderTableChangeNumberOfGuestsRequest;
import kitchenpos.eatinordertables.ui.request.EatInOrderTableCreateRequest;
import kitchenpos.eatinordertables.ui.response.EatInOrderTableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/eat-in-order-tables")
@RestController
public class EatInOrderTableRestController {
    private final EatInOrderTableCommandService eatInOrderTableCommandService;
    private final EatInOrderTableRepresentationService eatInOrderTableRepresentationService;

    public EatInOrderTableRestController(
        EatInOrderTableCommandService eatInOrderTableCommandService,
        EatInOrderTableRepresentationService eatInOrderTableRepresentationService
    ) {
        this.eatInOrderTableCommandService = eatInOrderTableCommandService;
        this.eatInOrderTableRepresentationService = eatInOrderTableRepresentationService;
    }

    @PostMapping
    public ResponseEntity<EatInOrderTableResponse> create(@RequestBody EatInOrderTableCreateRequest request) {
        final EatInOrderTableResponse response = eatInOrderTableCommandService.create(request);
        return ResponseEntity.created(URI.create("/api/eat-in-order-tables/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{eatInOrderTableId}/sit")
    public ResponseEntity<EatInOrderTableResponse> sit(@PathVariable UUID eatInOrderTableId) {
        return ResponseEntity.ok(eatInOrderTableCommandService.sit(eatInOrderTableId));
    }

    @PutMapping("/{eatInOrderTableId}/clear")
    public ResponseEntity<EatInOrderTableResponse> clear(@PathVariable UUID eatInOrderTableId) {
        return ResponseEntity.ok(eatInOrderTableCommandService.clear(eatInOrderTableId));
    }

    @PutMapping("/{eatInOrderTableId}/number-of-guests")
    public ResponseEntity<EatInOrderTableResponse> changeNumberOfGuests(
        @PathVariable UUID eatInOrderTableId,
        @RequestBody EatInOrderTableChangeNumberOfGuestsRequest request
    ) {
        return ResponseEntity.ok(eatInOrderTableCommandService.changeNumberOfGuests(eatInOrderTableId, request));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderTableResponse>> findAll() {
        return ResponseEntity.ok(eatInOrderTableRepresentationService.findAll());
    }
}
