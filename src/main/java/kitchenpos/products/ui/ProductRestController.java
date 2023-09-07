package kitchenpos.products.ui;

import kitchenpos.products.application.ProductService;
import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.dto.ProductDetailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/products")
@RestController
public class ProductRestController {
    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDetailResponse> create(@RequestBody final ProductCreateRequest request) {
        final ProductDetailResponse response = productService.create(request);
        return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
                .body(response);
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductDetailResponse> changePrice(@PathVariable final UUID productId, @RequestBody final ProductChangePriceRequest request) {
        return ResponseEntity.ok(productService.changePrice(productId, request));
    }

    @GetMapping
    public ResponseEntity<List<ProductDetailResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }
}
