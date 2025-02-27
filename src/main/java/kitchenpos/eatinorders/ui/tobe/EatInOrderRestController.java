package kitchenpos.eatinorders.ui.tobe;

import kitchenpos.eatinorders.application.OrderService;
import kitchenpos.eatinorders.application.tobe.EatInOrderService;
import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.ui.dto.EatInOrderCreateRequest;
import kitchenpos.eatinorders.ui.dto.EatInOrderCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
