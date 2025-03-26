package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.tobe.application.EatInOrderTableService;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
