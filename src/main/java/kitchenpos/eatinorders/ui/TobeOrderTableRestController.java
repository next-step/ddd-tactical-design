package kitchenpos.eatinorders.ui;

import kitchenpos.eatinorders.application.OrderTableService;
import kitchenpos.eatinorders.application.TobeOrderTableService;
import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/order-tables")
@RestController
public class TobeOrderTableRestController {
    private final TobeOrderTableService orderTableService;

    public TobeOrderTableRestController(final TobeOrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody final OrderTableRegisterRequest request) {
        final OrderTableResponse response = orderTableService.create(request);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response))
                .body(response);
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<?> sit(@Validated @PathVariable final OrderTableSitRequest request) {
        return ResponseEntity.ok(orderTableService.sit(request));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<?> clear(@Validated @PathVariable final OrderTableClearRequest request) {
        return ResponseEntity.ok(orderTableService.clear(request));
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<?> changeNumberOfGuests(
            @PathVariable final UUID orderTableId,
            @Validated @RequestBody final ChangeNumberOfGuestsRequest request
    ) {
        request.setId(orderTableId);
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(request));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
}
