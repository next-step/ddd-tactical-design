package kitchenpos.products.tobe.ui;

import kitchenpos.products.tobe.application.TobeProductService;
import kitchenpos.products.tobe.dto.request.ProductCreateRequest;
import kitchenpos.products.tobe.dto.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/products/tobe")
@RestController
public class TobeProductRestController {
    private final TobeProductService tobeProductService;

    public TobeProductRestController(final TobeProductService tobeProductService) {
        this.tobeProductService = tobeProductService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody final ProductCreateRequest request) {
        ProductResponse response = tobeProductService.create(request);
        return ResponseEntity.created(pathAfterProductCreation(response.id())).body(response);
    }

    private URI pathAfterProductCreation(UUID id) {
        return URI.create("/api/products/" + id);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(
            @PathVariable final UUID productId,
            @RequestBody final BigDecimal price
    ) {
        return ResponseEntity.ok(tobeProductService.changePrice(productId, price));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(tobeProductService.findAll());
    }
}
