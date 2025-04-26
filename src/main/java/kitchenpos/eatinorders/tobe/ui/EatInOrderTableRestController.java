package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.tobe.application.EatInOrderTableService;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTable;
import kitchenpos.eatinorders.tobe.ui.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableResponse;
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

@RequestMapping("/api/order-tables")
@RestController
public class EatInOrderTableRestController {

    private final EatInOrderTableService eatInOrderTableService;

    public EatInOrderTableRestController(final EatInOrderTableService eatInOrderTableService) {
        this.eatInOrderTableService = eatInOrderTableService;
    }

    @PostMapping
    public ResponseEntity<CreateEatInOrderTableResponse> create(@RequestBody final CreateEatInOrderTableRequest request) {
        final CreateEatInOrderTableResponse response = eatInOrderTableService.create(request);
        return ResponseEntity.created(URI.create("/api/order-tables"+ response.id()))
                .body(response);
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<EatInOrderTable> sit(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(eatInOrderTableService.sit(orderTableId));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<EatInOrderTable> clear(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(eatInOrderTableService.clear(orderTableId));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<EatInOrderTable> changeNumberOfGuests(
            @PathVariable final UUID orderTableId,
            @RequestBody final ChangeNumberOfGuestsRequest request
    ) {
        return ResponseEntity.ok(eatInOrderTableService.changeNumberOfGuests(orderTableId, request));
    }

    @GetMapping
    public ResponseEntity<List<EatInOrderTable>> findAll() {
        return ResponseEntity.ok(eatInOrderTableService.findAll());
    }
}
