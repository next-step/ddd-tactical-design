package kitchenpos.eatinorders.ui.tobe;


import kitchenpos.eatinorders.application.tobe.OrderTableService;
import kitchenpos.eatinorders.ui.dto.OrderTableCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/api/order-tables")
@RestController
public class OrderTableRestController {
    private final OrderTableService orderTableService;

    public OrderTableRestController(final OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping
    public ResponseEntity<OrderTableCreateResponse> create(@RequestBody final String taleName) {
        final OrderTableCreateResponse response = orderTableService.create(taleName);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
                .body(response);
    }
}
