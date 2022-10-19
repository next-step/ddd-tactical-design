package kitchenpos.products.ui;

import kitchenpos.products.application.ProductApplicationService;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.dto.ProductPriceChangeRequest;
import kitchenpos.products.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductApplicationService productService;

    public ProductRestController(final ProductApplicationService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody final ProductCreateRequest request) {
        final ProductResponse response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getProductId()))
            .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductResponse> changePrice(@PathVariable final UUID productId, @RequestBody final ProductPriceChangeRequest request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
